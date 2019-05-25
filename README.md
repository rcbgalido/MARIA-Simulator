# MARIA Simulator

A replication of MARIE Simulator (tool for showing the simulation on how programs run on a real "von Neumann architecture").

Instructions Implemented  
* LOAD X - Load contents of address X to AC  
* STORE X - Store the contents of AC to address X  
* ADD X - Add the contents of address X to AC  
* SUBT X - Subtract the contents of address X to AC  
* MULTI X - Multiply the contents of address X to AC  
* SQUARE X - Square the contents of AC and store to address X  
* SKIPCOMP X - Skip next instruction if AC is greater than the contents of address X  
* JUMP X - Load the value of X to PC  
* CnJUMP X - Clear all registers to default and load the value of X to PC  
* INPUT X - Input a value from keyboard and store to address X  
* OUTPUT - Output the value in AC to the display  
* HALT - Terminate program

<img src="/screenshot_01.png"/>

Sample .m files can be found in samples folder.
