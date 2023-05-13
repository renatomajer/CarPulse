package hr.fer.carpulse.domain.common.obd.commands;

import com.github.pires.obd.commands.PercentageObdCommand;

public class AbsoluteThrottlePositionBCommand extends PercentageObdCommand {

    /**
     * Default ctor.
     */
    public AbsoluteThrottlePositionBCommand() {
        super("01 47");
    }

    /**
     * Copy ctor.
     *
     * @param other a AbsoluteThrottlePositionB object.
     */
    public AbsoluteThrottlePositionBCommand(AbsoluteThrottlePositionBCommand other) {
        super(other);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "Absolute throttle position B";
    }
}
