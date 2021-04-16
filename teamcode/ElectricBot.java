package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Resources.Constants;
import org.firstinspires.ftc.teamcode.Resources.Names;
import org.firstinspires.ftc.teamcode.Subsystems.Drive;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeW;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;


public class ElectricBot {

    public Drive m_drive;
    public IntakeW m_intakeW;
    public Intake m_intake;
    public Shooter m_shooter;

    public ElectricBot(HardwareMap hardwareMap, Telemetry telemetry) {
        m_drive = new Drive(hardwareMap);
        m_intakeW = new IntakeW(hardwareMap,telemetry,Names.intakeWNames,false);
        m_intake = new Intake(hardwareMap,telemetry,Names.intakeMotorNames,false);
        m_shooter = new Shooter(hardwareMap,telemetry,Names.shooterMotorNames,false);

    }
}
