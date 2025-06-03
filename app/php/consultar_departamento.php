<?php
header('Content-Type: application/json');
ini_set('display_errors', 0);
error_reporting(0);

// Conexión
$conn = new mysqli("localhost", "root", "", "farmacia");

if ($conn->connect_error) {
    echo json_encode(['success' => false, 'message' => 'Error de conexión']);
    exit;
}

$idDepartamento = isset($_GET['idDepartamento']) ? $_GET['idDepartamento'] : null;

if (!$idDepartamento) {
    echo json_encode(['success' => false, 'message' => 'Falta ID']);
    exit;
}

$stmt = $conn->prepare("SELECT nombre FROM departamento WHERE idDepartamento = ?");
$stmt->bind_param("i", $idDepartamento);
$stmt->execute();
$stmt->bind_result($nombre);

if ($stmt->fetch()) {
    echo json_encode(['success' => true, 'idDepartamento' => $idDepartamento, 'nombre' => $nombre]);
} else {
    echo json_encode(['success' => false, 'message' => 'Departamento no encontrado']);
}

$stmt->close();
$conn->close();


