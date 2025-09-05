package com.hiutin.jetpackcomposetodo.common.extensions

import com.hiutin.jetpackcomposetodo.common.consts.Consts
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun LocalDate.toDateString(): String {
    return this.format(DateTimeFormatter.ofPattern(Consts.DATE_FORMAT))
}

fun LocalTime.toTimeString(): String {
    return this.format(DateTimeFormatter.ofPattern(Consts.TIME_FORMAT))
}
