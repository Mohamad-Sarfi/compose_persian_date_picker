package com.example.composeautomation.ui.datepicker

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
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

    // Start date
    var sMonth by remember {
        mutableStateOf(monthsList[month - 1])
    }

    var sYear by remember {
        mutableStateOf(year.toString())
    }

    var sDay by remember {
        mutableStateOf(today.toString())
    }


    // End date
    var eMonth by remember {
        mutableStateOf(monthsList[month - 1])
    }

    var eYear by remember {
        mutableStateOf(year.toString())
    }

    var eDay by remember {
        mutableStateOf(today.toString())
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
                    sMonth,
                    sYear ,
                    sDay ,
                    eMonth,
                    eYear,
                    eDay,
                    selectedPart,
                    setSDay = {sDay = it},
                    setSMonth = {sMonth = it},
                    setSYear = {sYear = it},
                    setEDay = {eDay = it},
                    setEMonth = {eMonth = it},
                    setEYear = {eYear = it},
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
                                            "day" to sDay,
                                            "month" to (monthsList.indexOf(sMonth) + 1).toString(),
                                            "year" to sYear
                                        ),
                                        mapOf(
                                            "day" to eDay,
                                            "month" to (monthsList.indexOf(eMonth) + 1).toString(),
                                            "year" to eYear
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
    sMonth: String,
    sYear : String,
    sDay : String,
    eMonth: String,
    eYear : String,
    eDay : String,
    selectedPart : String,
    setSDay : (String) -> Unit,
    setSMonth: (String) -> Unit,
    setSYear: (String) -> Unit,
    setEDay : (String) -> Unit,
    setEMonth: (String) -> Unit,
    setEYear: (String) -> Unit,
    setSelected : (String) -> Unit
){

    val width = LocalConfiguration.current.screenWidthDp
    val persianWeekDays = listOf("شنبه","یکشنبه","دوشنبه","سه شنبه","چهارشنبه","پنجشنبه","جمعه", )
    val monthsList = listOf("فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد","شهریور","مهر","آبان","آذر","دی","بهمن","اسفند",)
    val weekDay = JalaliCalendar(sYear.toInt(), monthsList.indexOf(sMonth) + 1, sDay.toInt()).dayOfWeek


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
                    .padding(vertical = 10.dp, horizontal = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                //Text(text = "انتخاب تاریخ", color = MaterialTheme.colors.onPrimary, style = MaterialTheme.typography.body2)
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
                        text = eMonth,
                        style = MaterialTheme.typography.h4,
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .clickable {
                                setSelected("month")
                            }
                    )
                    Text(text = eDay, style = MaterialTheme.typography.h4, color = MaterialTheme.colors.onPrimary)
                    Text(text = "تا",
                        style = MaterialTheme.typography.h4,
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier.padding(horizontal = 5.dp)
                    )

                    Text(
                        text = sMonth,
                        style = MaterialTheme.typography.h4,
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .clickable {
                                setSelected("month")
                            }
                    )
                    Text(text = sDay, style = MaterialTheme.typography.h4, color = MaterialTheme.colors.onPrimary)
                }
            }

            Days(
                sMonth,
                sDay,
                sYear,
                eMonth,
                eDay,
                eYear,
                setSDay = {setSDay(it)},
                setEDay = {setEDay(it)}
            )

        }
    }


}

@Composable
private fun Days(
    sMonth: String,
    sDay : String ,
    sYear: String ,
    eMonth: String,
    eDay : String ,
    eYear: String ,
    setSDay : (String) -> Unit,
    setEDay : (String) -> Unit,
    ){

    val monthsList = listOf("فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد","شهریور","مهر","آبان","آذر","دی","بهمن","اسفند",)
    val weekDays = listOf("شنبه","یکشنبه","دوشنبه","سه شنبه","چهارشنبه","پنجشنبه","جمعه", )

    var start by remember {
        mutableStateOf(false)
    }

    var end by remember {
            mutableStateOf(false)
    }

    var weekDay = JalaliCalendar(sYear.toInt(), monthsList.indexOf(sMonth) + 1 , 1).dayOfWeek

    var today = JalaliCalendar().day
    val thisMonth = JalaliCalendar().month -1
    Log.i("TAG_month","$thisMonth")

    var daysList = mutableListOf<String>()

    Log.i("TAG_weekday", "$weekDay")

    if (weekDay != 7){
        for (i in 1..weekDay){
            daysList.add(" ")
        }
    }

    if (monthsList.indexOf(sMonth) < 6){
        for (i in 1..31){
            daysList.add(i.toString())
        }
    } else {
        for (i in 1..30){
            daysList.add(i.toString())
            if (sDay.toInt() > 30){
                setSDay("30")
            }
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
            weekDays.forEach{
                Text(text = it, color = MaterialTheme.colors.primary.copy(.4f), style = MaterialTheme.typography.subtitle2)
            }
        }

        val years = listOf(sYear.toInt() - 1, sYear.toInt(), sYear.toInt() + 1)

        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)){
            items(12){ month ->
                Row(Modifier.fillMaxWidth().padding(horizontal = 30.dp, vertical = 5.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
                    Text(text = month.toString(), style = MaterialTheme.typography.body1, color = MaterialTheme.colors.onBackground)
                }
                LazyVerticalGrid(
                    columns = GridCells.Fixed(7),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentPadding = PaddingValues(horizontal =  15.dp, vertical = 0.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalArrangement = Arrangement.Center
                ){
                    items(daysList){
                        Surface(
                            modifier = Modifier
                                .padding(vertical = 4.dp)
                                .size(45.dp)
                                .clip(
                                    decideDayShape(
                                        it,
                                        sDay,
                                        eDay,
                                        sMonth,
                                        eMonth,
                                        sMonth
                                    )
                                )
                                .clickable {
                                    if (it != " ") {
                                        if (!start) {
                                            setSDay(it)
                                            start = true
                                        } else {
                                            setEDay(it)
                                            end = true
                                        }
                                    }
                                },
                            shape = decideDayShape(it, sDay, eDay, sMonth, eMonth, sMonth),
                            color = decideDayColor(it, sDay, eDay, sMonth, eMonth, sMonth),
                            border = BorderStroke( 1.dp, color = if (it == today.toString() && monthsList.indexOf(sMonth) == thisMonth) MaterialTheme.colors.primary else Color.Transparent)
                        ) {
                            Row(Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center) {
                                Text(text = it, style = MaterialTheme.typography.body1, color = if (sDay == it) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onBackground.copy(.7f))
                            }
                        }
                    }
                }
            }
        }



    }
}

private fun StartEndDate(value: String, sDay: String, sMonth: String, eDay: String, eMonth: String, start : Boolean, end: Boolean){
    if (start){

    }
}

@Composable
private fun decideDayColor(value : String, sDay: String, eDay: String, sMonth: String, eMonth: String, currentMonth : String) : Color {

    if (value != " "){
        if (value == sDay || value == eDay){
            return  MaterialTheme.colors.primary
        } else if (isBetweenStartEnd(value, sDay, eDay, sMonth, eMonth, currentMonth)) {
            return MaterialTheme.colors.primary.copy(.1f)
        }
        return Color.Transparent
    }
    return Color.Transparent
}

private fun decideDayShape(value : String, sDay: String, eDay: String, sMonth: String, eMonth: String, currentMonth : String) : RoundedCornerShape{

    val cornerRadius = 13.dp
    if (value != " "){
        if (value == sDay){
            return RoundedCornerShape(topStart = cornerRadius, bottomStart = cornerRadius)
        } else if (value == eDay) {
            return RoundedCornerShape(topEnd = cornerRadius, bottomEnd = cornerRadius)
        } else if (isBetweenStartEnd(value, sDay, eDay, sMonth, eMonth, currentMonth)){
            return RoundedCornerShape(0.dp)
        }
        return RoundedCornerShape(0.dp)
    }
    return RoundedCornerShape(0.dp)
}

private fun isBetweenStartEnd(value : String, sDay: String, eDay: String, sMonth: String, eMonth: String, currentMonth : String) : Boolean {
    if (value != " "){
        if (currentMonth == sMonth && currentMonth == eMonth){
            if (value.toInt() > sDay.toInt() && value.toInt() < eDay.toInt()){
                return true
            }
        } else if (currentMonth.toInt() in sMonth.toInt()..eMonth.toInt()){
            if (value.toInt() < eDay.toInt()){
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
