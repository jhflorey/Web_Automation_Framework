import random

suits=['hearts','diamonds','clubs','spades']
values=[1,2,3,4,5,6,7,8,9,10,11,12,13]
faces=["Ace","deuce","trey","four","five","six","seven","eight","nine","ten","Jack","Queen","King"]

class Card(object):
    def __init__(self,suit,value,face):
        self.suit=suit
        self.value=value
        self.face=face
    def show(self):
        #print ("This is the {} of {} which has value {}".format(self.face,self.suit,self.value))
        return "{},{},{}".format(self.face,self.suit,self.value)

class Deck(object):
    def __init__(self):
        deck=[]
        for s in suits:
            i=0
            while i<13:
                item=Card(s,values[i],faces[i])
                deck.append(item)
                i+=1
        self.cards = deck
    def show_cards_in_sentence(self):
        print ("The deck consists of the following",len(self.cards),"cards:")
        for c in self.cards:
            c.show()
        return        
    def show_cards_in_list(self): 
        print ("The deck consists of the following",len(self.cards),"cards:")
        cards_list=[]
        for c in self.cards:
            cards_list.append(c.show())
        print (cards_list)
        return        
    def shuffle(self): #Fisher-Yates shuffle
        for i in range(len(self.cards)-1,0,-1):
            randi=random.randint(0,i)
            if i==randi:
                continue
            self.cards[i],self.cards[randi] = self.cards[randi],self.cards[i]
        return self.cards    
    def deal_one_card(self):
        return self.cards.pop()
        
class Player(object):
    def __init__(self,name):
        self.name=name
        self.hand=[]
    def get_hand(self,deck,numcards):
        i=1
        while i<=numcards:
            self.hand.append(deck.deal_one_card())
            i+=1
        return self.hand
    def show_hand(self):
        for c in self.hand:
            c.show()
        return
    
b52 = Deck()
b52.show_cards_in_list()
b52.shuffle()

joe = Player('Joe')
joe.get_hand(newD,5)
joe.show_hand()

