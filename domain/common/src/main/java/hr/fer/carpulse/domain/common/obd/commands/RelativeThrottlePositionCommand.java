package hr.fer.carpulse.domain.common.obd.commands;

import com.github.pires.obd.commands.PercentageObdCommand;
import com.github.pires.obd.enums.AvailableCommandNames;

public class RelativeThrottlePositionCommand extends PercentageObdCommand {

    /**
     * Default ctor.
     */
    public RelativeThrottlePositionCommand() {
        super("01 45");
    }

    /**
     * Copy ctor.
     *
     * @param other a RelativeThrottlePositionCommand object.
     */
    public RelativeThrottlePositionCommand(RelativeThrottlePositionCommand other) {
        super(other);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return AvailableCommandNames.REL_THROTTLE_POS.getValue();
    }
}
