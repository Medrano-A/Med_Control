<?php


header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");


$host = "localhost";
$db = "farmacia";
$user = "root";
$password = "";

// conexión
$conn = new mysqli($host, $user, $password, $db);

// Verificar conexión
if ($conn->connect_error) {
    http_response_code(500);
    echo json_encode(["success" => false, "message" => "Conexión fallida: " . $conn->connect_error]);
    exit();
}

// Obtener datos JSON del cliente (Android)
$data = json_decode(file_get_contents("php://input"));

if (!isset($data->idDepartamento) || !isset($data->nombre)) {
    http_response_code(400);
    echo json_encode(["success" => false, "message" => "Datos incompletos."]);
    exit();
}

$idDepartamento = $conn->real_escape_string($data->idDepartamento);
$nombre = $conn->real_escape_string($data->nombre);

// Insertar en la base de datos
$sql = "INSERT INTO Departamento (idDepartamento, nombre) VALUES ('$idDepartamento', '$nombre')";

if ($conn->query($sql) === TRUE) {
    echo json_encode(["success" => true, "message" => "Registro insertado N°= " . $conn->insert_id]);
} else {
    echo json_encode(["success" => false, "message" => "Error al insertar: " . $conn->error]);
}

$conn->close();
?>
