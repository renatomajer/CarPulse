package hr.fer.carpulse.bluetooth

import android.bluetooth.BluetoothSocket
import android.util.Log
import com.github.pires.obd.commands.ObdCommand
import com.github.pires.obd.commands.SpeedCommand
import com.github.pires.obd.commands.engine.LoadCommand
import com.github.pires.obd.commands.engine.RPMCommand
import com.github.pires.obd.commands.engine.ThrottlePositionCommand
import com.github.pires.obd.commands.protocol.EchoOffCommand
import com.github.pires.obd.commands.protocol.LineFeedOffCommand
import com.github.pires.obd.commands.protocol.SelectProtocolCommand
import com.github.pires.obd.commands.protocol.TimeoutCommand
import com.github.pires.obd.enums.ObdProtocols
import com.github.pires.obd.exceptions.NoDataException
import hr.fer.carpulse.domain.common.obd.OBDReading
import hr.fer.carpulse.domain.common.obd.commands.AbsoluteThrottlePositionBCommand
import hr.fer.carpulse.domain.common.obd.commands.AcceleratorPedalPositionDCommand
import hr.fer.carpulse.domain.common.obd.commands.AcceleratorPedalPositionECommand
import hr.fer.carpulse.domain.common.obd.commands.RelativeThrottlePositionCommand
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class OBDCommunication(
    private val socket: BluetoothSocket
) {

    fun readData(): OBDReading {

        var obdReading: OBDReading

        try {
            // execute protocol commands
            EchoOffCommand().run(socket.inputStream, socket.outputStream)
            LineFeedOffCommand().run(socket.inputStream, socket.outputStream)
            TimeoutCommand(125).run(socket.inputStream, socket.outputStream)
            SelectProtocolCommand(ObdProtocols.AUTO).run(
                socket.inputStream,
                socket.outputStream
            )

            // Engine RPM
            val rpmCommand = RPMCommand()
            val rpmData = getDataFromCommand(rpmCommand)
            Log.d("PID", "${rpmCommand.name}: $rpmData")

            // Speed
            val speedCommand = SpeedCommand()
            val speedData = getDataFromCommand(speedCommand)
            Log.d("PID", "${speedCommand.name}: $speedData")

            // Relative throttle position
            val relativeThrottlePositionCommand = RelativeThrottlePositionCommand()
            val relativeThrottlePositionData = getDataFromCommand(relativeThrottlePositionCommand)
            Log.d(
                "PID",
                "${relativeThrottlePositionCommand.name}: $relativeThrottlePositionData"
            )

            // Absolute throttle position B
            val absoluteThrottlePositionBCommand = AbsoluteThrottlePositionBCommand()
            val absoluteThrottlePositionBData = getDataFromCommand(absoluteThrottlePositionBCommand)
            Log.d(
                "PID",
                "${absoluteThrottlePositionBCommand.name}: $absoluteThrottlePositionBData"
            )

            // Throttle position
            val throttlePositionCommand = ThrottlePositionCommand()
            val throttlePositionData = getDataFromCommand(throttlePositionCommand)
            Log.d(
                "PID",
                "${throttlePositionCommand.name}: $throttlePositionData"
            )

            // Accelerator pedal position E
            val acceleratorPedalPositionECommand = AcceleratorPedalPositionECommand()
            val acceleratorPedalPositionEData = getDataFromCommand(acceleratorPedalPositionECommand)
            Log.d(
                "PID",
                "${acceleratorPedalPositionECommand.name}: $acceleratorPedalPositionEData"
            )

            // Engine load
            val engineLoadCommand = LoadCommand()
            val engineLoadData = getDataFromCommand(engineLoadCommand)
            Log.d("PID", "${engineLoadCommand.name}: $engineLoadData")

            // Accelerator pedal position D
            val acceleratorPedalPositionDCommand = AcceleratorPedalPositionDCommand()
            val acceleratorPedalPositionDData = getDataFromCommand(acceleratorPedalPositionDCommand)
            Log.d(
                "PID",
                "${acceleratorPedalPositionDCommand.name}: $acceleratorPedalPositionDData"
            )

            val timestamp = System.currentTimeMillis()

            obdReading = OBDReading(
                rpm = rpmData,
                speed = speedData,
                relativeThrottlePosition = relativeThrottlePositionData,
                absoluteThrottlePositionB = absoluteThrottlePositionBData,
                throttlePosition = throttlePositionData,
                acceleratorPedalPositionE = acceleratorPedalPositionEData,
                engineLoad = engineLoadData,
                acceleratorPedalPositionD = acceleratorPedalPositionDData,
                timestamp = timestamp
            )

        } catch (exc: IOException) {
            Log.d("PID", "An exception occurred while communicating with device: " + exc.message)

            val timestamp = System.currentTimeMillis()
            obdReading = OBDReading(timestamp = timestamp)
        }

        return obdReading
    }

    private fun getDataFromCommand(
        command: ObdCommand,
        inputStream: InputStream = socket.inputStream,
        outputStream: OutputStream = socket.outputStream
    ): String {

        val data: String = try {
            command.run(socket.inputStream, socket.outputStream)
            command.formattedResult

        } catch (e: NoDataException) {
            OBDReading.NO_DATA
        }

        return data
    }

}