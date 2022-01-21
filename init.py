# This is a sample Python script.

# Press ⌃R to execute it or replace it with your code.
# Press Double ⇧ to search everywhere for classes, files, tool windows, actions, and settings.

rows, cols = (5, 5)
board = [[''] * cols] * rows

rabbit = {
    'name': 'r1',
    'space': {'x': 1, 'y': 1},
    'coordinate': {'x': 1, 'y': 2}
}

rabbit2 = {
    'name': 'r2',
    'space': {'x': 1, 'y': 1},
    'coordinate': {'x': 1, 'y': 2}
}

fox = {
    'name': 'f1',
    'space': {'x': 1, 'y': 2},
    'coordinate': {'x': 1, 'y': 2}
}


def add_item(i: object):
    id = i['name']
    cord = i['coordinate']
    spacex = i['space']['x']
    spacey = i['space']['y']
    for i in range(spacex - 1):
        print(i)
        board[cord['x'] + i][cord['y']] = id
    for j in range(spacey - 1):
        print(j)
        x = cord['x']
        y = cord['y']+j
        print(x)
        print(y)
        board[x][y] = id


def init_board():
    add_item(fox)


# print(fox)
# init_board()

board[1][2] = 'test'
print(board)
