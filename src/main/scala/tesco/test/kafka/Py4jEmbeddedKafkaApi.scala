package tesco.test.kafka

import io.github.embeddedkafka.EmbeddedKafka
import io.github.embeddedkafka.EmbeddedKafkaConfig
import java.util.HashMap
import scala.collection.JavaConverters._


object Py4jEmbeddedKafkaApi {
  val KAFKA_PORT_KEY = "kafkaPort"
  val ZOOKEEPER_PORT_KEY = "zooKeeperPort"

  private[kafka] def toEmbeddedConfig(config: HashMap[String, Integer]) = {
    val kafkaPort = config.asScala.get(KAFKA_PORT_KEY)
    .map(_.toInt)
    .getOrElse(EmbeddedKafkaConfig.defaultKafkaPort)
    val zooKeeperPort = config.asScala.get(ZOOKEEPER_PORT_KEY)
    .map(_.toInt)
    .getOrElse(EmbeddedKafkaConfig.defaultZookeeperPort)
    EmbeddedKafkaConfig(kafkaPort = kafkaPort, zooKeeperPort = zooKeeperPort)
  }

  def start() = EmbeddedKafka.start()

  def start(config: HashMap[String, Integer]) = {
    EmbeddedKafka.start()(toEmbeddedConfig(config))
  }

  def stop() = EmbeddedKafka.stop()

  def createCustomTopic(topic: String,
                        topicConfig: HashMap[String, String],
                        partitions: Integer,
                        replicationFactor: Integer,
                        config: HashMap[String, Integer]) = {
    EmbeddedKafka.createCustomTopic(topic,
                                    topicConfig.asScala.toMap,
                                    partitions.toInt,
                                    replicationFactor.toInt)(toEmbeddedConfig(config)).toOption
  }

  def createCustomTopic(topic: String,
                        topicConfig: HashMap[String, String],
                        partitions: Integer,
                        replicationFactor: Integer) = EmbeddedKafka.createCustomTopic(topic,
                                                                                      topicConfig.asScala.toMap,
                                                                                      partitions.toInt,
                                                                                      replicationFactor.toInt).toOption

  def consumeFirstStringMessageFrom(topic: String): String = EmbeddedKafka.consumeFirstStringMessageFrom(topic)

  def consumeFirstStringMessageFrom(topic: String, config: HashMap[String, Integer]): String = {
    EmbeddedKafka.consumeFirstStringMessageFrom(topic)(toEmbeddedConfig(config))
  }

  def publishToKafka(topic: String, message: String) = EmbeddedKafka.publishStringMessageToKafka(topic, message)

  def publishToKafka(topic: String, message: String, config: HashMap[String, Integer]) = {
    EmbeddedKafka.publishStringMessageToKafka(topic, message)(toEmbeddedConfig(config))
  }

  def deleteTopics(topics: java.util.List[String]) = EmbeddedKafka.deleteTopics(topics.asScala.toList)

  def deleteTopics(topics: java.util.List[String], config: HashMap[String, Integer]) = {
    EmbeddedKafka.deleteTopics(topics.asScala.toList)(toEmbeddedConfig(config))
  }
}
