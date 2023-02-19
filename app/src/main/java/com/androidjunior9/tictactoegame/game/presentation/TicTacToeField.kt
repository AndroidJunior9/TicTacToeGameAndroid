package com.androidjunior9.tictactoegame.game.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.androidjunior9.tictactoegame.game.data.GameState

@Composable
fun TicTacToeField(
    state: GameState,
    modifier: Modifier = Modifier,
    playerXColor: Color = Color.Red,
    player0Color: Color = Color.Green,
    onTapInField: (x:Int,y:Int) -> Unit
){
    Canvas(
        modifier = modifier
            .pointerInput(true){
                detectTapGestures{
                    val x = (3*it.x.toInt() / size.width)
                    val y = (3*it.y.toInt() / size.height)
                    onTapInField(x,y)
                }
            }
    ){
        drawField()
        state.board.forEachIndexed{y,_ ->
            state.board[y].forEachIndexed {x, player->
                val offset = Offset(
                    x = x*size.width* (1 / 3f) + size.width/6f,
                    y = y*size.height*(1/3f) + size.height/6f
                )
                if (player == 1){
                    drawX(
                        color = playerXColor,
                        center = offset,
                    )
                }else if(player == 2){
                    drawO(
                        color = player0Color,
                        center = offset
                    )
                }
            }
        }
    }

}

private fun DrawScope.drawO(
    color: Color,
    center:Offset,
    size:Size = Size(50.dp.toPx(),50.dp.toPx())
){
    drawCircle(
        color = color,
        center = center,
        radius = size.width/2f,
        style = Stroke(
            width = 3.dp.toPx(),

        )
    )
}

private fun DrawScope.drawX(
    color:Color,
    center:Offset,
    size:Size = Size(50.dp.toPx(),50.dp.toPx())
){
    drawLine(
        color = color,
        start = Offset(
            x = center.x-size.width/2f,
            y = center.y-size.height/2f
        ),
        end = Offset(
            x = center.x+size.width/2f,
            y = center.y+size.height/2f
        ),
        strokeWidth = 3.dp.toPx(),
        cap = StrokeCap.Round
    )

    drawLine(
        color = color,
        start = Offset(
            x = center.x+size.width/2f,
            y = center.y-size.height/2f
        ),
        end = Offset(
            x = center.x-size.width/2f,
            y = center.y+size.height/2f
        ),
        strokeWidth = 3.dp.toPx(),
        cap = StrokeCap.Round
    )
}

private fun DrawScope.drawField(){
    //First Vertical Line
    drawLine(
        color = Color.Black,
        start = Offset(
            x = size.width*(1/3f),
            y = 0f
        ),
        end = Offset(
            x = size.width*(1/3f),
            y = size.height
        ),
        strokeWidth = 3.dp.toPx(),
        cap = StrokeCap.Round
    )

    //Second Vertical Line
    drawLine(
        color = Color.Black,
        start = Offset(
            x = size.width*(2/3f),
            y = 0f
        ),
        end = Offset(
            x = size.width*(2/3f),
            y = size.height
        ),
        strokeWidth = 3.dp.toPx(),
        cap = StrokeCap.Round
    )

    //First Horizontal Line
    drawLine(
        color = Color.Black,
        start = Offset(
            x = 0f,
            y = size.height * (1/3f)
        ),
        end = Offset(
            x = size.width,
            y = size.height * (1/3f)
        ),
        strokeWidth = 3.dp.toPx(),
        cap = StrokeCap.Round
    )

    //Second Horizontal Line
    drawLine(
        color = Color.Black,
        start = Offset(
            x = 0f,
            y = size.height * (2/3f)
        ),
        end = Offset(
            x = size.width,
            y = size.height * (2/3f)
        ),
        strokeWidth = 3.dp.toPx(),
        cap = StrokeCap.Round
    )
}
















