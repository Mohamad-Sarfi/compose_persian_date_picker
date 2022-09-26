package com.example.composeautomation.ui.datepicker


import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ir.huri.jcal.JalaliCalendar
import kotlinx.coroutines.launch

@Composable
fun PersianRangeDatePicker(
    onDismiss : (Boolean) -> Unit,
    setDate : (List<Map<String, String>>) -> Unit
){
    val today = JalaliCalendar().day
    val month = JalaliCalendar().month
    val year = JalaliCalendar().year

    val monthsList = listOf("فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد","شهریور","مهر","آبان","آذر","دی","بهمن","اسفند",)

    var selectedPart by remember {
        mutableStateOf("main")
    }

    //list = [year, month, day]
    var startDate by remember {
        mutableStateOf(listOf(year, month, today))
    }

    var endDate by remember {
        mutableStateOf(listOf(year, month, today))
    }


    val width = LocalConfiguration.current.screenWidthDp

    Dialog(onDismissRequest = { onDismiss(true) }) {
        Card(
            modifier = Modifier
                .size(width = width.dp, height = 530.dp)
                .padding(5.dp),
            shape = RoundedCornerShape(10.dp),
            elevation = 4.dp,
            backgroundColor = MaterialTheme.colors.background
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 0.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                MainContent(
                    startDate,
                    endDate,
                    selectedPart,
                    setStartDate = {startDate = it},
                    setEndDate = {endDate = it},
                    setSelected = {selectedPart = it}
                )

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
                                    listOf(
                                        mapOf(
                                            "day" to startDate[2].toString(),
                                            "month" to startDate[1].toString(),
                                            "year" to startDate[0].toString()
                                        ),
                                        mapOf(
                                            "day" to endDate[2].toString(),
                                            "month" to endDate[1].toString(),
                                            "year" to endDate[0].toString()
                                        )
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
    startDate : List<Int>,
    endDate : List<Int>,
    selectedPart : String,
    setStartDate : (List<Int>) -> Unit,
    setEndDate : (List<Int>) -> Unit,
    setSelected : (String) -> Unit
){

    val width = LocalConfiguration.current.screenWidthDp
    val persianWeekDays = listOf("شنبه","یکشنبه","دوشنبه","سه شنبه","چهارشنبه","پنجشنبه","جمعه", )
    val monthsList = listOf("فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد","شهریور","مهر","آبان","آذر","دی","بهمن","اسفند",)
    val weekDay = JalaliCalendar(startDate[0], startDate[1], startDate[2]).dayOfWeek


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column() {

            Row(
                Modifier
                    .height(45.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.primary)
                    .padding(vertical = 13.dp, horizontal = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(Icons.Default.Edit,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onPrimary,
                    modifier = Modifier
                        .clickable {

                        }
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.primary)
                    .padding(vertical = 10.dp, horizontal = 25.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ){
                    Text(
                        text = monthsList[endDate[1]],
                        style = MaterialTheme.typography.h4,
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                    )
                    Text(text = endDate[2].toString(), style = MaterialTheme.typography.h4, color = MaterialTheme.colors.onPrimary)
                    Text(text = "تا",
                        style = MaterialTheme.typography.h4,
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier.padding(horizontal = 5.dp)
                    )

                    Text(
                        text = monthsList[startDate[1]],
                        style = MaterialTheme.typography.h4,
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                    )
                    Text(text = startDate[2].toString(), style = MaterialTheme.typography.h4, color = MaterialTheme.colors.onPrimary)
                }
            }

            Days(
                startDate,
                endDate,
                setStartDate = {setStartDate(it)} ,
                setEndDay = {setEndDate(it)}
            )

        }
    }
}

@Composable
private fun Days(
    startDate: List<Int>,
    endDate: List<Int>,
    setStartDate : (List<Int>) -> Unit,
    setEndDay : (List<Int>) -> Unit,
    ){

    val monthsList = listOf("فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد","شهریور","مهر","آبان","آذر","دی","بهمن","اسفند",)
    val weekDays = listOf("شنبه","یکشنبه","دوشنبه","سه شنبه","چهارشنبه","پنجشنبه","جمعه", )

    var start by remember {
        mutableStateOf(false)
    }

    var end by remember {
            mutableStateOf(false)
    }

    var weekDay = JalaliCalendar(startDate[0].toInt(), startDate[1] + 1 , 1).dayOfWeek

    var today = JalaliCalendar().day
    val thisMonth = JalaliCalendar().month



    val monthRange = mutableListOf<Int>()

    for (m in thisMonth - 3 .. thisMonth + 3){
        monthRange.add(m)
    }

    var gridState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = 1){
        scope.launch {
            gridState.scrollToItem(monthRange.indexOf(thisMonth-1))
        }
    }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            weekDays.forEach {
                Text(text = it, color = MaterialTheme.colors.primary.copy(.4f), style = MaterialTheme.typography.subtitle2)
            }
        }

        //val years = listOf(sYear.toInt() - 1, sYear.toInt(), sYear.toInt() + 1)

        LazyColumn(
            modifier = Modifier
            .fillMaxWidth().height(290.dp),
            state = gridState
        ){
            items(monthRange){ month ->

                val daysList = daysOfMonth(listOf(startDate[0], month), startDate, endDate)

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp, vertical = 0.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
                    Text(text = monthsList[month], style = MaterialTheme.typography.body1, color = MaterialTheme.colors.onBackground.copy(.5f))
                }
                LazyVerticalGrid(
                    columns = GridCells.Fixed(7),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(290.dp),
                    contentPadding = PaddingValues(horizontal =  15.dp, vertical = 0.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalArrangement = Arrangement.Center
                ){
                    items(daysList){
                        Surface(
                            modifier = Modifier
                                .padding(vertical = 2.dp)
                                .size(42.dp)
                                .clip(
                                    decideDayShape(
                                        it,
                                        month,
                                        startDate,
                                        endDate,
                                    )
                                )
                                .clickable {
                                    setStartEndDates(
                                        it,
                                        month,
                                        startDate,
                                        endDate,
                                        start,
                                        { v -> setStartDate(v) },
                                        { v -> setEndDay(v) },
                                        {v -> end = v},
                                        { v -> start = v }
                                    )
                                },
                            shape = decideDayShape(it, month, startDate, endDate, ),
                            color = decideDayColor(it, startDate, endDate, end, month),
                            border = BorderStroke( 1.dp, color = if (it == today.toString() && startDate[1] == thisMonth) MaterialTheme.colors.primary else Color.Transparent)
                        ) {
                            Row(Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center) {
                                Text(text = it, style = MaterialTheme.typography.body1, color = decideDayText(it, startDate, endDate, month))
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun setStartEndDates(
    day: String,
    month : Int,
    startDate: List<Int>,
    endDate: List<Int>,
    start : Boolean,
    setStartDate: (List<Int>) -> Unit,
    setEndDate: (List<Int>) -> Unit,
    setEndBool : (Boolean) -> Unit,
    setStartBool : (Boolean) -> Unit
){
    if (day != " "){
        if (!start) {
            setStartDate(listOf(startDate[0], month , day.toInt()))
            setStartBool(true)
        } else if (day.toInt() == startDate[2] && month == startDate[1]){
            setStartBool(false)
            setEndBool(false)
            
        } else if (month == startDate[1]) {
            if (day.toInt() < startDate[2]){
                setEndDate(startDate)
                setStartDate(listOf(startDate[0], month , day.toInt()))
                setEndBool(true)
            } else {
                setEndDate(listOf(startDate[0], month , day.toInt()))
                setEndBool(true)
            }
        } else if (month < startDate[1]){
            setEndDate(startDate)
            setStartDate(listOf(startDate[0], month , day.toInt()))
            setEndBool(true)
        } else {
            setEndDate(listOf(startDate[0], month , day.toInt()))
            setEndBool(true)
        }
    }
}


private fun calculateFirstDayOffset(date : List<Int>) : Int{
    return JalaliCalendar(date[0].toInt(), date[1] + 1 , 1).dayOfWeek
}

private fun daysOfMonth(date: List<Int>, startDate: List<Int>, endDate: List<Int>) : List<String>{
    var daysList = mutableListOf<String>()

    val weekDay = calculateFirstDayOffset(date)
    if (weekDay != 7){
        for (i in 1..weekDay){
            daysList.add(" ")
        }
    }

    if (startDate[1] < 6){
        for (i in 1..31){
            daysList.add(i.toString())
        }
    } else {
        for (i in 1..30){
            daysList.add(i.toString())
        }
    }
    return daysList
}

@Composable
private fun decideDayColor(day : String, startDate: List<Int>, endDate: List<Int>, end : Boolean, month : Int) : Color {

    if (day != " "){
        if ((day == startDate[2].toString() && month == startDate[1]) || (day == endDate[2].toString() && month == endDate[1])){
            return  MaterialTheme.colors.primary
        } else if (isBetweenStartEnd(day, startDate, endDate, month) && end) {
            Log.i("TAG_yyy", "Found")
            return MaterialTheme.colors.primary.copy(.1f)
        }
        return Color.Transparent
    }
    return Color.Transparent
}

@Composable
private fun decideDayText(day : String, startDate: List<Int>, endDate: List<Int>, month : Int) : Color {
    if (day != " "){
        if ((startDate[2].toString() == day && month == startDate[1]) || (endDate[2].toString() == day && month == endDate[1])){
            return MaterialTheme.colors.onPrimary
        } else {
            return MaterialTheme.colors.onBackground.copy(.7f)
        }
    }
    return Color.Transparent
}

private fun decideDayShape(day : String, month: Int, startDate: List<Int>, endDate: List<Int>) : RoundedCornerShape{

    val cornerRadius = 13.dp
    if (day != " "){
        if (day == startDate[2].toString() && month == startDate[1]){
            return RoundedCornerShape(topStart = cornerRadius, bottomStart = cornerRadius)
        } else if (day == endDate[2].toString() && month == endDate[1]) {
            return RoundedCornerShape(topEnd = cornerRadius, bottomEnd = cornerRadius)
        } else if (isBetweenStartEnd(day, startDate, endDate, month)){
            return RoundedCornerShape(0.dp)
        }
        return RoundedCornerShape(0.dp)
    }
    return RoundedCornerShape(0.dp)
}

private fun isBetweenStartEnd(value : String, startDate: List<Int>, endDate: List<Int>, currentMonth : Int) : Boolean {
    if (value != " "){
        if (currentMonth == startDate[1] && currentMonth == endDate[1]){
            if (value.toInt() > startDate[2] && value.toInt() < endDate[2]){
                return true
            }
        } else if (currentMonth == startDate[1]){
            if (value.toInt() > startDate[2]){
                return true
            }
        } else if (currentMonth < endDate[1] && currentMonth > startDate[1]){
            return true
        } else if (currentMonth <= endDate[1] && currentMonth > startDate[1]){
            if (value.toInt() < endDate[2]){
                return true
            }
        }
        return false
    }
    return false
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
