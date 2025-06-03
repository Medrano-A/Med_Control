<?php

header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

$host = "localhost";
$db = "farmacia";
$user = "root";
$password = "";

$conn = new mysqli($host, $user, $password, $db);

if ($conn->connect_error) {
    http_response_code(500);
    echo json_encode(["success" => false, "message" => "Conexión fallida: " . $conn->connect_error]);
    exit();
}

$data = json_decode(file_get_contents("php://input"));

// Solo nombreDoctor es obligatorio
if (!isset($data->nombreDoctor)) {
    http_response_code(400);
    echo json_encode(["success" => false, "message" => "Datos incompletos."]);
    exit();
}

$nombreDoctor = $conn->real_escape_string($data->nombreDoctor);
$especialidad = isset($data->especialidad) ? "'" . $conn->real_escape_string($data->especialidad) . "'" : "NULL";
$jvpm = isset($data->jvpm) ? "'" . $conn->real_escape_string($data->jvpm) . "'" : "NULL";
$telefonoDoctor = isset($data->telefonoDoctor) ? "'" . $conn->real_escape_string($data->telefonoDoctor) . "'" : "NULL";
$correoDoctor = isset($data->correoDoctor) ? "'" . $conn->real_escape_string($data->correoDoctor) . "'" : "NULL";

// No incluyas idDoctor en el INSERT porque es autoincremental
$sql = "INSERT INTO Doctor (nombreDoctor, especialidad, jvpm, telefonoDoctor, correoDoctor)
        VALUES ('$nombreDoctor', $especialidad, $jvpm, $telefonoDoctor, $correoDoctor)";

if ($conn->query($sql) === TRUE) {
    echo json_encode(["success" => true, "message" => "Registro insertado N°= " . $conn->insert_id]);
} else {
    echo json_encode(["success" => false, "message" => "Error al insertar: " . $conn->error]);
}

$conn->close();
?>
