package hr.fer.carpulse.domain.common.obd.commands;

import com.github.pires.obd.commands.PercentageObdCommand;

public class AcceleratorPedalPositionECommand extends PercentageObdCommand {

    /**
     * Default ctor.
     */
    public AcceleratorPedalPositionECommand() {
        super("01 4A");
    }

    /**
     * Copy ctor.
     *
     * @param other a AcceleratorPedalPositionE object.
     */
    public AcceleratorPedalPositionECommand(AcceleratorPedalPositionECommand other) {
        super(other);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "Accelerator pedal position E";
    }
}
