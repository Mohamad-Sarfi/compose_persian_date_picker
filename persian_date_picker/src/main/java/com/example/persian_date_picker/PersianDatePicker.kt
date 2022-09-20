package com.example.persian_date_picker

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ir.huri.jcal.JalaliCalendar
import kotlinx.coroutines.launch

@Composable
fun PersianDatePicker(
    onDismiss : (Boolean) -> Unit,
    setDate : (Map<String, String>) -> Unit
){

    val today = JalaliCalendar().day
    val month = JalaliCalendar().month
    val year = JalaliCalendar().year

    val monthsList = listOf("فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد","شهریور","مهر","آبان","آذر","دی","بهمن","اسفند",)

    var selectedPart by remember {
        mutableStateOf("main")
    }
    var mMonth by remember {
        mutableStateOf(monthsList[month - 1])
    }

    var mYear by remember {
        mutableStateOf(year.toString())
    }

    var mDay by remember {
        mutableStateOf(today.toString())
    }

    val width = LocalConfiguration.current.screenWidthDp
    val height = LocalConfiguration.current.screenHeightDp * .6

    Dialog(onDismissRequest = { onDismiss(true) }) {
        Card(
            modifier = Modifier
                .size(width = width.dp, height = 450.dp)
                .padding(5.dp),
            shape = RoundedCornerShape(10.dp),
            elevation = 4.dp,
            backgroundColor = Color(0xffe3f2fd)
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 0.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Crossfade(targetState = selectedPart) { selected ->
                    when (selected){
                        "main" -> Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                            MainContent(mMonth, mYear, mDay,{mDay = it}, setMonth = {mMonth = it}, setYear = {mYear = it} ){selectedPart = it}
                        }
                        "month" -> Months(mMonth,{selectedPart = "main"} ){mMonth = it}
                        "year" -> Years(mYear, setYear = {mYear = it}, changeSelectedPart = {selectedPart = "main"})
                        else ->
                            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                                MainContent(mMonth, mYear, mDay, {mDay = it},setMonth = {mMonth = it}, setYear = {mYear = it} ){selectedPart = it}
                            }
                    }
                }


                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                    ,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {

                    Text(
                        text = "لغو",
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier
                            .clickable {
                                onDismiss(true)
                            }
                            .padding(horizontal = 10.dp)
                    )
                    Text(
                        text = "تایید",
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier
                            .clickable {
                                onDismiss(true)
                                setDate(
                                    mapOf(
                                        "day" to mDay,
                                        "month" to (monthsList.indexOf(mMonth) + 1).toString(),
                                        "year" to mYear
                                    )
                                )
                            }
                            .padding(horizontal = 15.dp)
                    )

                }
            }

        }
    }
}

@Composable
private fun MainContent(
    mMonth: String,
    mYear : String,
    mDay : String,
    setDay : (String) -> Unit,
    setMonth: (String) -> Unit,
    setYear: (String) -> Unit,
    setSelected : (String) -> Unit
){

    val width = LocalConfiguration.current.screenWidthDp
    val persianWeekDays = listOf("شنبه","یکشنبه","دوشنبه","سه شنبه","چهارشنبه","پنجشنبه","جمعه", )
    val monthsList = listOf("فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد","شهریور","مهر","آبان","آذر","دی","بهمن","اسفند",)
    val weekDay = JalaliCalendar(mYear.toInt(), monthsList.indexOf(mMonth) + 1, mDay.toInt()).dayOfWeek


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column() {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.primaryVariant)
            ){
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = persianWeekDays[weekDay - 1], style = MaterialTheme.typography.body1, color = MaterialTheme.colors.onPrimary)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.primary)
                    .padding(vertical = 18.dp, horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.ChevronLeft,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onPrimary,
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .clip(CircleShape)
                            .clickable {
                                decreaseMonth(
                                    mMonth,
                                    mYear,
                                    setMonth = { setMonth(it) },
                                    setYear = { setYear(it) })
                            }
                    )

                    Text(
                        text = mYear,
                        style = MaterialTheme.typography.h4,
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier.clickable {
                            setSelected("year")
                        }
                    )

                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = mMonth,
                        style = MaterialTheme.typography.h3,
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .clickable {
                                setSelected("month")
                            }
                    )
                    Text(text = mDay, style = MaterialTheme.typography.h3, color = MaterialTheme.colors.onPrimary)
                    Icon(
                        Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onPrimary,
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .clickable {
                                increaseMonth(
                                    mMonth,
                                    mYear,
                                    setMonth = { setMonth(it) },
                                    setYear = { setYear(it) })
                            }
                    )
                }
            }

            Days(mMonth, mDay, mYear, setDay = {setDay(it)}, changeSelectedPart = {})
        }
    }


}

private fun increaseMonth(mMonth: String, mYear: String,setMonth: (String) -> Unit, setYear: (String) -> Unit) {
    val monthsList = listOf("فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد","شهریور","مهر","آبان","آذر","دی","بهمن","اسفند",)
    if (monthsList.indexOf(mMonth) < 10){
        setMonth(monthsList[monthsList.indexOf(mMonth) + 1])
    } else {
        setMonth(monthsList[0])
        setYear((mYear.toInt() + 1).toString())
    }
}

private fun decreaseMonth(mMonth: String, mYear: String,setMonth: (String) -> Unit, setYear: (String) -> Unit) {
    val monthsList = listOf("فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد","شهریور","مهر","آبان","آذر","دی","بهمن","اسفند",)
    if (monthsList.indexOf(mMonth) > 0){
        setMonth(monthsList[monthsList.indexOf(mMonth) - 1])
    } else {
        setMonth(monthsList[11])
        setYear((mYear.toInt() - 1).toString())
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Months(mMonth : String, setSelected: () -> Unit ,setMonth : (String) -> Unit){

    val monthsList = listOf("فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد","شهریور","مهر","آبان","آذر","دی","بهمن","اسفند",)

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(23.dp),
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.Center
    ){
        items(monthsList){
            Surface(
                modifier = Modifier
                    .padding(5.dp)
                    .size(75.dp)
                    .clip(CircleShape)
                    .clickable {
                        setMonth(it)
                        setSelected()
                    }
                ,
                shape = CircleShape,
                color = if (mMonth == it) MaterialTheme.colors.primary else Color.Transparent,

                ) {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = it, color = if (mMonth == it) MaterialTheme.colors.onPrimary else MaterialTheme.colors.primary, style = MaterialTheme.typography.h5)
                }
            }
        }
    }


}



@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Days(mMonth: String,mDay : String , mYear: String , setDay : (String) -> Unit, changeSelectedPart : (String) -> Unit){

    val monthsList = listOf("فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد","شهریور","مهر","آبان","آذر","دی","بهمن","اسفند",)
    val weekDays = listOf("شنبه","یکشنبه","دوشنبه","سه شنبه","چهارشنبه","پنجشنبه","جمعه", )

    var weekDay = JalaliCalendar(mYear.toInt(), monthsList.indexOf(mMonth) + 1 , 1).dayOfWeek

    var today = JalaliCalendar().day
    val thisMonth = JalaliCalendar().month -1
    Log.i("TAG_month","$thisMonth")

    var daysList = mutableListOf<String>()

    for (i in 1..weekDay){
        daysList.add(" ")
    }

    if (monthsList.indexOf(mMonth) < 6){
        for (i in 1..31){
            daysList.add(i.toString())
        }
    } else {
        for (i in 1..30){
            daysList.add(i.toString())
            if (mDay.toInt() > 30){
                setDay("30")
            }
        }
    }

    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        weekDays.forEach{
            Text(text = it, color = MaterialTheme.colors.primary.copy(.3f), style = MaterialTheme.typography.subtitle2)
        }
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal =  15.dp, vertical = 3.dp),
        verticalArrangement = Arrangement.Top,
        horizontalArrangement = Arrangement.Center
    ){
        items(daysList){
            Surface(
                modifier = Modifier
                    .padding(vertical = 1.dp)
                    .size(45.dp)
                    .clip(CircleShape)
                    .clickable {
                        if (it != " ") {
                            changeSelectedPart("main")
                            setDay(it)
                        }
                    },
                shape = CircleShape,
                color = if (mDay == it) MaterialTheme.colors.primary else Color.Transparent,
                border = BorderStroke( 1.dp, color = if (it == today.toString() && monthsList.indexOf(mMonth) == thisMonth) MaterialTheme.colors.primary else Color.Transparent)
            ) {
                Row(Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center) {
                    Text(text = it, style = MaterialTheme.typography.h5, color = if (mDay == it) Color.White else MaterialTheme.colors.primary)
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Years(mYear: String, setYear : (String) -> Unit, changeSelectedPart: (String) -> Unit){

    var years = mutableListOf<Int>()
    for (y in 1430 downTo 1350){
        years.add(y)
    }

    var gridState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = 1){
        scope.launch {
            gridState.scrollToItem(28)
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(18.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        state = gridState
    ){
        items(years){
            Surface(
                modifier = Modifier
                    .padding(15.dp)
                    .size(60.dp)
                    .clip(CircleShape)
                    .clickable {
                        changeSelectedPart("main")
                        setYear(it.toString())
                    },
                shape = CircleShape,
                color = if (mYear == it.toString()) MaterialTheme.colors.primary else Color.Transparent
            ) {
                Row(Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center) {
                    Text(text = it.toString(), style = MaterialTheme.typography.h5, color = if (mYear == it.toString()) Color.White else MaterialTheme.colors.primary)
                }
            }
        }
    }

}










