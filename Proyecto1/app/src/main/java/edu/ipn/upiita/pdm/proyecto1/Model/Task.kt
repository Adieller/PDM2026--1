package edu.ipn.upiita.pdm.proyecto1.Model

data class Task(
    val id: String = java.util.UUID.randomUUID().toString(),
    var title: String,
    var LocalDate: String,
    var isCompleted: Boolean = false,
    var priority: Priority = Priority.LOW
)

enum class Priority{
    LOW, MEDIUM, HIGH
}
