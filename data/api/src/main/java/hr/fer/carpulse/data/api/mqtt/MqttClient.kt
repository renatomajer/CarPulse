package hr.fer.carpulse.data.api.mqtt

import android.content.Context
import android.util.Log
import android.widget.Toast
import hr.fer.carpulse.domain.common.contextual.data.Message
import info.mqtt.android.service.Ack
import info.mqtt.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import java.util.*

class MQTTClient(
    private val context: Context,
    serverURI: String = SERVER_URI,
    clientID: String = ""
) {
    private var mqttClient = MqttAndroidClient(context, serverURI, clientID, Ack.AUTO_ACK)

    private var isConnected = false
    private val messageQueue: Queue<Message> = LinkedList()

    fun connect(
        cbConnect: IMqttActionListener = defaultCbConnect,
        cbClient: MqttCallback = defaultCbClient
    ) {
        if (isConnected) return

        Log.d("MQTT", "Trying to connect!")
        mqttClient.setCallback(cbClient)
        val options = MqttConnectOptions()

        try {
            mqttClient.connect(options, null, cbConnect)
        } catch (e: MqttException) {
            Log.d("MQTT", e.printStackTrace().toString())
        }
    }

    fun publish(
        topic: String,
        msg: String,
        qos: Int = 1,
        retained: Boolean = false,
        isFromQueue: Boolean = false,
        cbPublish: IMqttActionListener = defaultCbPublish(msg, topic)
    ) {
        // add message to queue if there are already other messages waiting to be sent, or if the connection is not established
        if ((!isFromQueue && messageQueue.isNotEmpty()) || !isConnected) {
            val message = Message(topic = topic, msg = msg)
            messageQueue.add(message)
            return
        }

        try {
            val message = MqttMessage()
            message.payload = msg.toByteArray()
            message.qos = qos
            message.isRetained = retained
            mqttClient.publish(topic, message, null, cbPublish)
        } catch (e: MqttException) {
            Log.d("MQTT", e.printStackTrace().toString())
        }
    }

    fun disconnect(cbDisconnect: IMqttActionListener = defaultCbDisconnect) {

        if (!isConnected) return

        try {
            mqttClient.disconnect(null, cbDisconnect)
        } catch (e: MqttException) {
            Log.d("MQTT", e.printStackTrace().toString())
        }
    }

    private val defaultCbConnect = object : IMqttActionListener {
        override fun onSuccess(asyncActionToken: IMqttToken?) {
            Log.d("MQTT", "Connection success")

            isConnected = true

            Toast.makeText(context, "MQTT Connection success", Toast.LENGTH_SHORT).show()

            // Connection established - start sending messages if there are any in the queue
            publishMessageFromQueue()
        }

        override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            Log.d("MQTT", "Connection failure: ${exception.toString()}")

            Toast.makeText(
                context, "MQTT Connection fails: ${exception.toString()}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private val defaultCbClient = object : MqttCallback {
        override fun messageArrived(topic: String?, message: MqttMessage?) {
            val msg = "Receive message: ${message.toString()} from topic: $topic"
            Log.d("MQTT", msg)

            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

        override fun connectionLost(cause: Throwable?) {
            Log.d("MQTT", "Connection lost ${cause.toString()}")
            isConnected = false
        }

        override fun deliveryComplete(token: IMqttDeliveryToken?) {
            Log.d("MQTT", "Delivery complete")
        }
    }

    private fun defaultCbPublish(message: String, topic: String) =
        object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                val msg = "Publish message to topic: $topic"
                Log.d("MQTT", msg)

                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

                // publish next message from queue
                publishMessageFromQueue()
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                Log.d("MQTT", "Failed to publish message to topic")
            }
        }

    private val defaultCbDisconnect = object : IMqttActionListener {
        override fun onSuccess(asyncActionToken: IMqttToken?) {
            Log.d("MQTT", "Disconnected")
            isConnected = false

            Toast.makeText(context, "MQTT Disconnection success", Toast.LENGTH_SHORT).show()

            // Remove all messages from the queue
            messageQueue.clear()
        }

        override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            Log.d("MQTT", "Failed to disconnect")
        }
    }

    private fun publishMessageFromQueue() {

        // send first message from the queue
        val message = messageQueue.poll()
        if (message != null) {
            publish(
                topic = message.topic,
                msg = message.msg,
                qos = 0,
                retained = true,
                isFromQueue = true
            )
        }
    }

    companion object {
        private const val SERVER_URI = "tcp://broker.hivemq.com:1883"
    }
}

