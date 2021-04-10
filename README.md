<h1 align="center">IoT Enabled Low Cost SPO2 and Heart Rate Monitoring System For Quarantine Covid19 Patients</h1>
<p align="center">
</p>

<a href="https://hack36.com"> <img src="http://bit.ly/BuiltAtHack36" height=20px> </a>


## Introduction

- Home/Institutional quarantine Covid19 patients face many problems regarding their critical health parameter monitoring due to very contagious nature of virus and misses timely assistance in case of emergency. 

- Due to Highly infectious nature of Covid19 virus , visiting patient is restricted , and doctors/ family members are not aware of present condition of patients.

- Virus mainly infects Lungs and patient becomes breathless.

- Our product not only monitor SPO2 and heart rate of the patient; real time data can be viewed by doctors and family members using IoT cloud/ Mobile app.

## Solution

- The Device will continuously monitor the **heart rate** and **SPO2** of the patient. It will measure and display heart rate of patient regularly.

- IoT Enabled feature of device will help in remote monitoring of the patient. 

- Realtime data and previous values can be viewed/ monitored through IoT cloud or Mobile App. 

**Monitoring patient’s health remotely by collecting patient’s data continuously can help preventive care and doctors can also diagnose acute complications earlier with Internet of things this can be done by employing sensors to collect patients physiological information send them to the cloud to analyze and store the information and forward the analysis to caregivers**


![System Architecture](https://github.com/shaurya0406/Team-Aiders-Hack36/blob/main/Data/System-Architecture.png)

## Working Principle

* The Sensor consists of a IR LED and IR detector.

* When heart pumps blood through blood vessels, finger becomes slightly more opaque to IR and so less light reaches the detector. 

* Calculated Heart Rate and Oxygen saturation in Blood, which is called SPO2 is detected and displayed on OLED display. 

* Real time data is uploaded to Cloud for remote Monitoring

**Pulse oximetry is a non-invasive method used to measure heart rate and oxygen saturation both. Oximeter device using pulse oximetry method has a sensor consisting of two LED lights that emit in the red 650 nm and infrared 950 nm spectrum.**

**This sensor is usually placed on the fingers or ears or on the skin which is not too thick so that both the light frequency can easily penetrate the tissues. After that the absorption of the red and infrared light is measured by photodiode and the amount will depend on the amount of oxygen in the blood. Oxygen rich haemoglobin ( Oxyhemoglobin) absorbs more infrared light while those without oxygen (Deoxyhemoglobin) absorb red light.**

## Challenges Faced

* We were using Scenform SDK with AR core to implement AR for Yoga in our app. As SceneForm has been depreciated and it was very hard to work with it as most of the tutorials on the net were based on and have used Sceneform PlugIn which is no longer supported by Android Studio.
So after going through a lot of articles we found this piece of code.

'''
sceneform.asset('sampledata/meditationladywireframe.gltf',
        'default',
        'sampledata/meditationladywireframe.sfa',
        'src/main/assets/model'
)
'''
* Initially we were getting very vague and completely off-the chart values for Spo2 from the sensor. After going through various documentation and the manufacturer's Datasheet, we got to know that an internal temperature sensor has to be used to calliberate the Spo2 sub-systems and this tesolved our issue.

* To make our device Fault-Tolerant, we have implemented Priority based requests from the ThingSpeak Cloud. So the Caregivers can be assigned priority levels and this will help us to manage multiple requests at the same time.

## Android App


Our app enables **real-time monitoring** of patients health parameters like **Heart-Rate** and **Spo2** and along with it also displays Graphs based on data history for easy analysis.

All the data can be sent to the doctor with a single click of a button or automatically based on user's preference.

Covid-patients not only suffer from this dangerous disease but also suffer from mental depression, loneliness and physical inactivity.
To keep the patients entertained we have entertainement tab which will redirect the users to their desired streaming service.
To prevent the patients from stressing their eyes and worsening their health by watching on a small screen of mobile device, we give an alert to the user after 20 minutes of watch time to return back to the app where they will be presented with AR models of various Yoga exercises. 
This way the patients can also take care of their physical health.

The implementation of AR will help the patients to visualize the postures properly and exercise will become a fun activity for them.


## Future Enhancements

* Developed prototype delivered acceptable accuracy with medical devices.

* It can be game changer solution in COVID19 monitoring, which mainly focuses in SPO2 monitoring.

* Prototype can be commercialised to make cost effective solution for Heart Rate/SPO2 monitoring in home without any complex setup.

Team Name: OP Squad

* [Khushi Singh](https://github.com/khushisinghvit)
* [Shubham Shankaram](https://github.com/shubhamji88)
* [Yati Rastogi](https://github.com/yatirastogi)
* [Shaurya Chandra](https://github.com/shaurya0406)


### Made at:
<a href="https://hack36.com"> <img src="http://bit.ly/BuiltAtHack36" height=20px> </a>