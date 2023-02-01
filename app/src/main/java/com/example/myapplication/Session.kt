package com.example.myapplication

import java.time.LocalDate
import java.util.*

class Session {
    var id: UUID = UUID.randomUUID()
    var Time: Long = 0
    var Distance: Int = 0
    var Name: String = "Nazwa"
    var Date: LocalDate = LocalDate.now()

    constructor(time: Long, dist: Int, name: String) {
        Time = time
        Distance = dist
        Name = name
    }
    constructor() {
    }
}