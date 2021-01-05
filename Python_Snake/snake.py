import random
#import an initialize pygame
import pygame
from pygame.locals import *
SCREEN_W=1000
SCREEN_H=600
class snake(object):
    def __init__(self):
        self.positions=[(SCREEN_W*SCREEN_H)/400]
        self.direction='X'
        self.positions[0]=[SCREEN_W/2,SCREEN_H/2]
        #self.positions[0]=[SCREEN_W/2,SCREEN_H/2]
        self.head=self.positions[0]
        
    def getLength(self):
        return len(self.positions)
    
    def getHead(self):
        return self.head
    
    def changeDir(self,pointer):
        self.direction=pointer

    def move(self):
        tmpAhead=[]
        tmpSelf=[]
        for i in range(len(self.positions)):
            if(self.positions[i]==self.head):
                tmpAhead=self.head.copy()
                if(self.direction=='L'):
                    self.head[0]-=20
                if(self.direction=='R'):
                    self.head[0]+=20
                if(self.direction=='U'):
                    self.head[1]-=20
                if(self.direction=='D'):
                    self.head[1]+=20
                if(self.head in self.positions[1:len(self.positions)]):
                    return True
            elif(self.direction!='X'):
                tmpSelf=self.positions[i].copy()
                self.positions[i]=tmpAhead
                tmpAhead=tmpSelf
        return False
                
                    
    def reset(self):
        self.positions=[(SCREEN_W*SCREEN_H)/400]
        self.direction='X'
        self.positions[0]=(SCREEN_W/2,SCREEN_H/2)
        self.head=self.positions[0]

    def drawSnake(self, surface):
        for cell in self.positions:
            pygame.draw.rect(surface,"yellow",pygame.Rect(cell[0],cell[1],19,19))
        pygame.display.flip()

    def eatFood(self):
        new=self.head.copy()
        for i in range(5):
            if(self.direction=='L'):
                new[0]+=4
            if(self.direction=='R'):
                new[0]-=4
            if(self.direction=='U'):
                new[1]+=4
            if(self.direction=='D'):
                new[1]-=4
            self.positions.append(new)
        

class food(object):
    def __init__(self):
        
        def init_vals(): #initializes all possible coordinate values and returns them as a tuple(left,top)
            coordsl=[]
            coordst=[]
            for i in range(20,SCREEN_H-20,20):
                coordst.append(i)
            for i in range(20,SCREEN_W-20,20):
                coordsl.append(i)
            return coordsl,coordst
        
        self.coords=init_vals()
        self.position=[0,0]

    def rand_pos(self):
        random.seed()
        self.position=[random.choice(self.coords[0]),random.choice(self.coords[1])]
    
    def drawFood(self, surface):
        pygame.draw.rect(surface,"white",pygame.Rect(self.position[0],self.position[1],20,20))
        pygame.display.flip()
    

def main():
    #SCREEN_W=(int) (input("Enter window width: "))
    #SCREEN_H=(int) (input("Enter window height: "))
    pygame.init()
    pygame.display.set_caption("Python Snake")
    #setup screen, draw background
    screen = pygame.display.set_mode((SCREEN_W,SCREEN_H))
    background=pygame.Surface((SCREEN_W,SCREEN_H))
    #screen.fill((0,51,153))
    left_wall= pygame.Rect(0,0,20,SCREEN_H)
    top_wall= pygame.Rect(0,0,SCREEN_W,20)
    right_wall= pygame.Rect(SCREEN_W-20,0,20,SCREEN_H)
    bottom_wall= pygame.Rect(0,SCREEN_H-20,SCREEN_W,20)
    center= pygame.Rect(20,20,SCREEN_W-40,SCREEN_H-40)

    #draws walls on all four sides of the screen
    pygame.draw.rect(background,(153,0,0),left_wall)
    pygame.draw.rect(background,(153,0,0),top_wall)
    pygame.draw.rect(background,(153,0,0),right_wall)
    pygame.draw.rect(background,(153,0,0),bottom_wall)
    pygame.draw.rect(background,(0,51,153),center)

    #draws starting snake position, starting food position
    snake1=snake()
    food1=food()
    snake1.drawSnake(background)
    food1.rand_pos()
    food1.drawFood(background)
    screen.blit(background,(0,0))
    
    #drwas length counter in the bottom rigth corner
    font = pygame.font.SysFont("Ariel",24)
    text = font.render("Length: " + (str)(snake1.getLength()), True, "black",(153,0,0))
    pygame.display.flip()
    
    #starts a clock
    clock= pygame.time.Clock()
    while(1):
        clock.tick(10)
        events = pygame.event.get()
        #checks for input to change snakes direction
        for event in events:
            if event.type==pygame.KEYDOWN:
                if event.key == pygame.K_LEFT and (snake1.direction!='R' or snake1.getLength()==1):
                    snake1.direction='L'
                if event.key == pygame.K_RIGHT and (snake1.direction!='L' or snake1.getLength()==1):
                    snake1.direction='R'
                if event.key == pygame.K_UP and (snake1.direction!='D' or snake1.getLength()==1):
                    snake1.direction='U'
                if event.key == pygame.K_DOWN and (snake1.direction!='U' or snake1.getLength()==1):
                    snake1.direction='D'
            if event.type==pygame.QUIT:
                pygame.quit()
        #handles movement of the snake
        if(snake1.move()):
            displayLose(screen,snake1)
            break
        #checks if the snake has collided with the food
        if(snake1.getHead()==food1.position):
            food1.rand_pos()
            snake1.eatFood()
            #updates counter text
            text = font.render("Length: " + (str)(snake1.getLength()), True, "black",(153,0,0))
        #redraws snake's new position ontop of the background
        pygame.draw.rect(background,(0,51,153),center)
        snake1.drawSnake(background)
        food1.drawFood(background)
        screen.blit(background,(0,0))
        screen.blit(text,(SCREEN_W-120,SCREEN_H-20))
        pygame.display.flip()             
        if(snake1.getHead()[0]<20 or snake1.getHead()[0]>SCREEN_W-40 or snake1.getHead()[1]<20 or snake1.getHead()[1]>SCREEN_H-40):
            displayLose(screen,snake1)
            break
##        if(snake1.getHead() in snake1.positions[1:len(snake1.positions)]):
##            print("You Lost :(")
##            break

def displayLose(surface,snake):
    #creates and displays pop-up window showing the players score and a button to restart
    while(True):
        popup=pygame.Surface((400,200))
        box=pygame.Rect(0,0,200,100)
        pygame.draw.rect(popup,"black",box)
        surface.blit(popup,(SCREEN_W/2-200,SCREEN_H/2-100))
        
        font = pygame.font.SysFont("Ariel",32)
        smallfont=pygame.font.SysFont("Ariel",20)
        text = font.render("Game Over ", True, "white","black")
        text2= font.render("You ended with a score of: " +(str)(snake.getLength()), True, "white","black") 
        buttonText=smallfont.render("Play Again",True,"black")
        pygame.draw.rect(surface,"white",[SCREEN_W/2-50,SCREEN_H/2+50,100,20])
        surface.blit(text,(SCREEN_W/2-60,SCREEN_H/2-50))
        surface.blit(text2,(SCREEN_W/2-150,SCREEN_H/2))
        surface.blit(buttonText,(SCREEN_W/2-35,SCREEN_H/2+53))
        pygame.display.flip()

        #clock for testing purposes
        #clock=pygame.time.Clock()
        while(1):
            #clock.tick(1)
            mouse =pygame.mouse.get_pos()
            # print(mouse)
            # print(mouse[0]>=SCREEN_W/2-50)
            # print(mouse[0]<=SCREEN_W/2+100)
            # print(mouse[1]>=SCREEN_H/2+50)
            # print(mouse[1]<=SCREEN_H/2+20)
            events=pygame.event.get()
            for event in events:
                if event.type==pygame.QUIT:
                    pygame.quit()
                
                if event.type==pygame.MOUSEBUTTONDOWN:
                    if mouse[0]>=SCREEN_W/2-50 and mouse[0]<=SCREEN_W/2+50 and mouse[1]>=SCREEN_H/2+50 and mouse[1]<=SCREEN_H/2+70:
                        
                        main()

            if mouse[0]>=SCREEN_W/2-50 and mouse[0]<=SCREEN_W/2+100 and mouse[1]>=SCREEN_H/2+50 and mouse[1]<=SCREEN_H/2+70:  
                print("poggers")     
                pygame.draw.rect(surface,(150,150,150),[SCREEN_W/2-50,SCREEN_H/2+50,100,20])
                surface.blit(buttonText,(SCREEN_W/2-35,SCREEN_H/2+53))

            else:
                pygame.draw.rect(surface,"white",[SCREEN_W/2-50,SCREEN_H/2+50,100,20])
                surface.blit(buttonText,(SCREEN_W/2-35,SCREEN_H/2+53))

            pygame.display.flip()
                

    
if __name__ =="__main__":
    main()
