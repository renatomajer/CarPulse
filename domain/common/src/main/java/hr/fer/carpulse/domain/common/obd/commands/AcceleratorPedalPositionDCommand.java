package hr.fer.carpulse.domain.common.obd.commands;

import com.github.pires.obd.commands.PercentageObdCommand;

public class AcceleratorPedalPositionDCommand extends PercentageObdCommand {

    /**
     * Default ctor.
     */
    public AcceleratorPedalPositionDCommand() {
        super("01 49");
    }

    /**
     * Copy ctor.
     *
     * @param other a AcceleratorPedalPositionD object.
     */
    public AcceleratorPedalPositionDCommand(AcceleratorPedalPositionDCommand other) {
        super(other);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "Accelerator pedal position D";
    }
}
