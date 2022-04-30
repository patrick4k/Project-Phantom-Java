# Kennedy_CS225_FINAL_PROJECT_GITHUB
SRMSim is an application the simulates the internal ballistics of a solid rocket motor

Basic functionality:
Run SRMSimApp.java to open the application to get started. The application should look like the following:

<img width="742" alt="Screen Shot 2022-04-30 at 12 39 17 PM" src="https://user-images.githubusercontent.com/87878545/166114454-2a4cf848-dacb-47d3-9118-ec446384ebff.png">

On this screen you can create your motor from scatch or you can import a preset motor, or another previously saved motor.

To access the import menu, select import motor from the file menu:

<img width="159" alt="Screen Shot 2022-04-30 at 12 40 51 PM" src="https://user-images.githubusercontent.com/87878545/166114529-def3c624-109c-4186-9b83-ff2afcd370e5.png">

<img width="299" alt="Screen Shot 2022-04-30 at 12 41 09 PM" src="https://user-images.githubusercontent.com/87878545/166114539-e447deb6-8350-44c9-9335-f46c80015d1c.png">

Here you can import your saved motor name to import its values. Some preset motor names are "presetMotor", "presetTubular", "presetCross" and "presetMoon".

Typle your desired motor name into the box and hit import when ready.

<img width="748" alt="Screen Shot 2022-04-30 at 12 43 39 PM" src="https://user-images.githubusercontent.com/87878545/166114602-db305b8e-b2be-47b9-9bff-530b4b1779e1.png">

All values stored in the imported file will be updated on the motor design screen. Feel free to edit, alter and modify, or just simulate as it is!

Grain configuration can be edited in the "Current Grain Configuration" drop down:

<img width="328" alt="Screen Shot 2022-04-30 at 12 45 44 PM" src="https://user-images.githubusercontent.com/87878545/166114673-331cd3c4-f213-4a26-99dc-588b357ebb1f.png">

Select a grain you wish to update or remove here. Add a new grain to grain configuration by designing the grain and hitting the add button.

Simulation can be activated by hitting the "Calculate Motor Performance" button in the bottom right corner. Note simulation time varies per motor complexity and size.

<img width="750" alt="Screen Shot 2022-04-30 at 12 48 09 PM" src="https://user-images.githubusercontent.com/87878545/166114759-08cf3ca0-39f1-455a-98d1-ba35f4605c72.png">

The plot pane is shown right after simulation, here you can select a graph to display the performance of your motor via a drop down menu.

<img width="434" alt="Screen Shot 2022-04-30 at 12 49 46 PM" src="https://user-images.githubusercontent.com/87878545/166114804-c875ebe8-fa29-429f-921e-841acde07998.png">

By hitting the "View Static Results" button, static performance values will be displayed to the user.

<img width="260" alt="Screen Shot 2022-04-30 at 12 51 40 PM" src="https://user-images.githubusercontent.com/87878545/166114872-2493f642-3a65-44e9-8667-b30237f49f8e.png">

The file menu can be utilized to import and export file, a ".motor" file can store properties of motor design, and a csv file can be generated for user analysis of data collected.

The options menu is used to toggle english or SI units. The program defaults into SI units but is compatible with english inputs or outputs. Corresponding conversions will be calculated to ensure continuous results.

