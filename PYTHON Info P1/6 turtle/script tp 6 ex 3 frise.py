from turtle import *
from figuresBasiques import*
clear()
up()
goto(-600,0)
down()
for i in range(20):
    carre(30,"red")
    up()
    forward(35)
    down()
    
    trianglebas(30,"blue")
    up()
    forward(45)
    right(90)
    forward(30)
    left(90)
    down()
    
    cercle(30,"yellow")
    up()
    forward(20)
    left(90)
    forward(20)
    right(90)
    down()
    
    etoile5(30,"black")
    up()
    forward(45)
    left(90)
    forward(10)
    right(90)
    down()
