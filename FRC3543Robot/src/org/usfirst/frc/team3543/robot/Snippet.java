package org.usfirst.frc.team3543.robot;

public class Snippet {
	<xml version="1.0">
	<dashboard>
		<widget field="Close Gear Holder" type="Command" class="edu.wpi.first.smartdashboard.gui.elements.Command">
			<location x="341" y="655"/>
		</widget>
		<widget field="Open Gear Holder" type="Command" class="edu.wpi.first.smartdashboard.gui.elements.Command">
			<location x="344" y="689"/>
		</widget>
		<widget field="Drive Forward 12in" type="Command" class="edu.wpi.first.smartdashboard.gui.elements.Command">
			<location x="0" y="638"/>
		</widget>
		<widget field="BallPickupCommand" type="Command" class="edu.wpi.first.smartdashboard.gui.elements.Command">
			<location x="0" y="579"/>
		</widget>
		<widget field="Gear holder servo open angle" type="Number" class="edu.wpi.first.smartdashboard.gui.elements.TextBox">
			<location x="242" y="735"/>
			<height>22</height>
		</widget>
		<widget field="Drive Forward" type="Command" class="edu.wpi.first.smartdashboard.gui.elements.Command">
			<location x="226" y="774"/>
		</widget>
		<widget field="Drive Forward Distance" type="Number" class="edu.wpi.first.smartdashboard.gui.elements.TextBox">
			<location x="28" y="783"/>
			<width>178</width>
		</widget>
		<widget field="Gear Drop Right Initial Distance" type="Number" class="edu.wpi.first.smartdashboard.gui.elements.TextBox">
			<location x="857" y="120"/>
		</widget>
		<widget field="Gear Drop Left Initial Distance" type="Number" class="edu.wpi.first.smartdashboard.gui.elements.TextBox">
			<location x="863" y="90"/>
		</widget>
		<widget field="Lower Robot" type="Command" class="edu.wpi.first.smartdashboard.gui.elements.Command">
			<location x="77" y="443"/>
		</widget>
		<widget field="Lift Robot" type="Command" class="edu.wpi.first.smartdashboard.gui.elements.Command">
			<location x="92" y="414"/>
		</widget>
		<static-widget class="edu.wpi.first.smartdashboard.gui.elements.Label">
			<location x="26" y="684"/>
			<property name="Text" value="Misc. Test Commands"/>
		</static-widget>
		<static-widget class="edu.wpi.first.smartdashboard.gui.elements.Label">
			<location x="20" y="52"/>
			<property name="Text" value="Calibration"/>
		</static-widget>
		<widget field="Gear Finder Location" type="String" class="edu.wpi.first.smartdashboard.gui.elements.TextBox">
			<location x="567" y="270"/>
			<width>385</width>
		</widget>
		<widget field="Gear Finder Speed Gain" type="Number" class="edu.wpi.first.smartdashboard.gui.elements.TextBox">
			<location x="61" y="192"/>
		</widget>
		<widget field="Gear Finder Curve Gain" type="Number" class="edu.wpi.first.smartdashboard.gui.elements.TextBox">
			<location x="62" y="154"/>
		</widget>
		<widget field="Default Rotation Gain" type="Number" class="edu.wpi.first.smartdashboard.gui.elements.TextBox">
			<location x="73" y="116"/>
		</widget>
		<widget field="Default Linear Gain" type="Number" class="edu.wpi.first.smartdashboard.gui.elements.TextBox">
			<location x="84" y="82"/>
		</widget>
		<widget field="GearDrop inital static angle (deg)" type="Number" class="edu.wpi.first.smartdashboard.gui.elements.TextBox">
			<location x="513" y="124"/>
		</widget>
		<widget field="Rotate 90 degrees" type="Command" class="edu.wpi.first.smartdashboard.gui.elements.Command">
			<location x="23" y="706"/>
		</widget>
		<widget field="Autonomous Mode" type="String" class="edu.wpi.first.smartdashboard.gui.elements.TextBox">
			<location x="593" y="52"/>
		</widget>
		<widget field="Zig Zag" type="Command" class="edu.wpi.first.smartdashboard.gui.elements.Command">
			<location x="86" y="742"/>
		</widget>
		<widget field="Right Encoder" type="Number" class="edu.wpi.first.smartdashboard.gui.elements.TextBox">
			<location x="916" y="310"/>
			<width>170</width>
		</widget>
		<widget field="Position and Approach Gear Drop" type="Command" class="edu.wpi.first.smartdashboard.gui.elements.Command">
			<location x="18" y="507"/>
		</widget>
		<widget field="GearDrop inital static distance (in)" type="Number" class="edu.wpi.first.smartdashboard.gui.elements.TextBox">
			<location x="507" y="87"/>
			<height>25</height>
		</widget>
		<widget field="Wheel encoder distance per pulse" type="Number" class="edu.wpi.first.smartdashboard.gui.elements.TextBox">
			<location x="5" y="230"/>
		</widget>
		<widget field="Distance Remaining" type="Number" class="edu.wpi.first.smartdashboard.gui.elements.TextBox">
			<location x="590" y="159"/>
		</widget>
		<widget field="Approach Gear Drop" type="Command" class="edu.wpi.first.smartdashboard.gui.elements.Command">
			<location x="294" y="537"/>
		</widget>
		<widget field="Left Encoder" type="Number" class="edu.wpi.first.smartdashboard.gui.elements.TextBox">
			<location x="719" y="310"/>
			<height>24</height>
		</widget>
		<widget field="Locate Gear Drop" type="Command" class="edu.wpi.first.smartdashboard.gui.elements.Command">
			<location x="308" y="504"/>
		</widget>
		<widget field="Lift Gain" type="Number" class="edu.wpi.first.smartdashboard.gui.elements.TextBox">
			<location x="155" y="269"/>
		</widget>
		<widget field="Gyro" type="Number" class="edu.wpi.first.smartdashboard.gui.elements.TextBox">
			<location x="567" y="311"/>
		</widget>
		<widget field="RaiseBallChuteCommand" type="Command" class="edu.wpi.first.smartdashboard.gui.elements.Command">
			<location x="260" y="333"/>
		</widget>
		<widget field="LowerBallChuteCommand" type="Command" class="edu.wpi.first.smartdashboard.gui.elements.Command">
			<location x="258" y="364"/>
		</widget>
		<static-widget class="edu.wpi.first.smartdashboard.gui.elements.MjpgStreamViewerImpl">
			<location x="524" y="350"/>
			<width>707</width>
			<height>401</height>
			<property name="MJPG Server URL" value="http://10.35.43.77/mjpg/video.mjpg"/>
		</static-widget>
		<static-widget class="edu.wpi.first.smartdashboard.gui.elements.ConnectionIndicator">
			<location x="225" y="8"/>
		</static-widget>
		<widget field="Auto Selector" type="String" class="edu.wpi.first.smartdashboard.gui.elements.TextBox">
			<location x="22" y="14"/>
		</widget>
		<widget field="TankDriveWithJoysticks" type="Command" class="edu.wpi.first.smartdashboard.gui.elements.Command">
			<location x="15" y="360"/>
		</widget>
		<widget field="ArcadeDriveWithJoystick" type="Command" class="edu.wpi.first.smartdashboard.gui.elements.Command">
			<location x="7" y="328"/>
		</widget>
	</dashboard>
	<live-window>
	</live-window>
	</xml>
}

