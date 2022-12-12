//Duy Nguyen
//dnguy171
//m20752544

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define MAX_ROOM 20
#define MAX_CHAR 255

typedef struct Room {
  unsigned long id;
  char item[MAX_CHAR];
  int next_room_num; 
  unsigned long* next;
} Room_t; 

unsigned long calc_abs(unsigned long a, unsigned long b) {
  return a > b ? a - b : b - a;
}
void createRoom(Room_t* room, unsigned long id, char* item, int n, unsigned long* next);
void updateRoom();
Room_t* find(Room_t* list, unsigned long id); //find a room in list by id
int isNextRoom(Room_t* room, unsigned long id); //check input room is next to current room
unsigned long* str_to_array(char* s, int n); // covert next room string to array
int main() {
  char* line[MAX_ROOM];
  Room_t* list = NULL;
  int n = 0;

  // count number of room
  while(1) {
    line[n] = malloc(MAX_CHAR * sizeof(char));
    fgets(line[n], MAX_CHAR, stdin);
    if(line[n][0] == '0') break;
    n += 1;
  }

  // allocate room
  list = malloc(n *sizeof(Room_t));
  // create room
  for(int i = 0; i < n; i++) {
    int token1 = atoi(strtok(line[i], " "));    
    char* token2 = strtok(NULL, " ");
    char* token3 = strtok(NULL, ","); 
    if(token3)
      token3[strlen(token3) - 1] = 0;
    else
      token3 = "";

    int count = 1;
    for(int i = 0; i < strlen(token2); i++)
      if(token2[i] == ',')
        count += 1;
    createRoom(list + i, token1, token3, count, str_to_array(token2, count));
  }
  // free data of line
  for(int i = 0; i < n; i++) {
    free(line[i]);
  }

  char buf[MAX_CHAR];
  fgets(buf, MAX_CHAR, stdin);
  unsigned long start = atoi(strtok(buf, ","));
  unsigned long end = atoi(strtok(NULL, ","));
  unsigned long cur = start;
  char curItem[MAX_CHAR] = "";

  // user play
  printf("Welcome to the dungeon.\n");
  while(1) {
    // get current room
    Room_t* curRoom = find(list, cur);
    // print current room status
    printf("You are in room %ld", cur);
    if(strlen(curRoom->item) > 0) {
      printf(", on the ground is a %s", curRoom->item); 
    }
    printf(". Nearby are rooms %ld", curRoom->next[0]);
    for(int i = 1; i < curRoom->next_room_num; i+=1) {
      printf(", %ld", curRoom->next[i]);
    }
    printf("\n> ");
    // get input from player
    fgets(buf, MAX_CHAR, stdin);
    unsigned int room = atoi(buf);
    // handle player input
    if(room == 0) {
      // drop item if player has
      if(strlen(curItem) > 0) {
        printf("You drop the %s.\n", curItem);
      }
      // pick up a item if current room has
      if(strlen(curRoom->item) > 0) {
        printf("You pick up the %s.\n", curRoom->item);
      }
      updateRoom(curRoom, curItem);
    } else if(isNextRoom(curRoom, room)) {
      // get dragon room
      if(room == end) {
        printf("You enter a cavernous room containing piles of gold and other riches.\nA large red dragon swoops down upon you from above.\n");
        // win if player has sword
        if(strcmp(curItem, "sword") == 0) {
          printf("You instinctively slash out with the sword, and mortally wound the beast!\nYou win!\n");
        } else { // otherwise player lose
          if(strlen(curItem) > 0) {
            printf("The %s is useless against the mighty dragon.\n", curItem);
          } else {
            printf("You have nothing against the dragon.\n");
          }
          printf("You are burned to a crisp by its flaming breath.\n(On the bright side, I hear burning alive is only like the second or third worst way to die).\nYou are dead.\n");
        }
        // deallocate list room and end program
        for(int i = 0; i < n; i++) {
          free(list[i].next);
        }
        free(list);
        return 0;
      }
      if(calc_abs(cur, end) > calc_abs(room, end)) {
        printf("You're getting warmer!\n");
      } else if(calc_abs(cur, end) == calc_abs(room, end)) {
        printf("You're neither warmer nor colder.\n");
      } else {
        printf("You're getting colder!\n");
      }
      cur = room;
    }
  }

  return 0;
}

void createRoom(Room_t* room, unsigned long id, char* item, int n, unsigned long* next) {
  room->id = id;
  strcpy(room->item, item);
  room->next_room_num = n;
  room->next = next;
}

void updateRoom(Room_t* room, char* item) {
  char tmp[MAX_CHAR];
  strcpy(tmp, room->item);
  strcpy(room->item, item);
  strcpy(item, tmp);
}

Room_t* find(Room_t* list, unsigned long id) {
  for(int i = 0; list + i != NULL; i += 1) {
    if(list[i].id == id) {
      return list + i;
    }
  }
  return NULL;
}

int isNextRoom(Room_t* room, unsigned long id) {
  for(int i = 0; i < room->next_room_num; i++) {
    if(room->next[i] == id) {
      return 1;
    }
  }
  return 0;
}

unsigned long* str_to_array(char* s, int n) {
  unsigned long* arr = malloc(n * sizeof(unsigned long));
  arr[0] = atoi(strtok(s + 1, ","));
  for(int i = 1; i < n; i += 1) {
    arr[i] = atoi(strtok(NULL, ","));
  }
  return arr;
}
