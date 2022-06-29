# UCSD Zoo Trip Planner/Navigation App

## Description
An android app built from scratch that enables users to:
- search for exhibits at the zoo
- select their favorites
- create a plan enabling them to visit their favorite exhibits as efficiently as possible
- see step-by-step directions for traveling between exhibits
- view directions to previous exhibits in the plan
- skip certain exhibits in the plan
<img src="https://user-images.githubusercontent.com/72843831/176351849-b4ec4dd2-17c4-414f-99b2-2c5f8ac66124.png" width="250" height="450">
<img src="https://user-images.githubusercontent.com/72843831/176351860-28c4afd9-b9ae-4285-9576-86a92d3ebc5c.png" width="250" height="450">
<img src="https://user-images.githubusercontent.com/72843831/176351865-07574e17-93f9-4909-ae7f-66a4e83e8c00.png" width="250" height="450">


### A cleaned APK file can be downloaded in the [Google Drive link](https://drive.google.com/file/d/1AuxvPTuCmphF0qiDeKmnQoeOA9Cg_Y3n/view?usp=sharing). Previous versions of this repo (not APK) are accessible in this repository.

### This application uses JSON data, NOT Google Maps or some similar third party service, to calculate directions. There are six non-image files in app/src/main/assets representing two different maps.

The second of the two maps below is the one the final apk uses  for directions, but older versions of this repository may use the first map.

[Map](https://github.com/EthanLDot/UC_San_Diego_Zoo/blob/7c10dbb15c0b9cb84e5f8b92173b234bf0365505/app/src/main/assets/map1.png) for sample_edge_info, sample_node_info, and sample_zoo_graph.json:

![image](https://user-images.githubusercontent.com/72843831/176349171-dac31548-baa8-44b5-b8c2-3612ec5e5921.png)

[Map](https://github.com/EthanLDot/UC_San_Diego_Zoo/blob/7c10dbb15c0b9cb84e5f8b92173b234bf0365505/app/src/main/assets/map2.png) for exhibit_info, trail_info, and zoo_graph.json:
![image](https://user-images.githubusercontent.com/72843831/176349342-1d3597d5-bfc4-4054-bccb-b584b67a1a13.png)


Created by Ethan Lee, Jeff (Ren-Si) Lin, Arvin Manila, Daniel Ryu, David (Hao-Ting) Tso, and James Zhang (all of the University of California, San Diego) for CSE 110 (Software Engineering) Spring 2022. Completed June 2022.

Questions about this repository can be directed to ethanlee2337@gmail.com
