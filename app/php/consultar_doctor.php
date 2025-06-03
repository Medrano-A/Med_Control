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

$idDoctor = isset($_GET['idDoctor']) ? $_GET['idDoctor'] : null;

if (!$idDoctor) {
    echo json_encode(['success' => false, 'message' => 'Falta ID']);
    exit;
}

// Prepara y ejecuta la consulta
$stmt = $conn->prepare("SELECT nombreDoctor, especialidad, jvpm, telefonoDoctor, correoDoctor FROM doctor WHERE idDoctor = ?");
$stmt->bind_param("i", $idDoctor);
$stmt->execute();
$stmt->bind_result($nombreDoctor, $especialidad, $jvpm, $telefonoDoctor, $correoDoctor);

if ($stmt->fetch()) {
    echo json_encode([
        'success' => true,
        'idDoctor' => $idDoctor,
        'nombreDoctor' => $nombreDoctor,
        'especialidad' => $especialidad,
        'jvpm' => $jvpm,
        'telefonoDoctor' => $telefonoDoctor,
        'correoDoctor' => $correoDoctor
    ]);
} else {
    echo json_encode(['success' => false, 'message' => 'Doctor no encontrado']);
}

$stmt->close();
$conn->close();
