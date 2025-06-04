<?php
header('Content-Type: application/json');
ini_set('display_errors', 0);
error_reporting(0);

$respuesta = ['resultado' => 0, 'message' => 'Error desconocido'];

// Obtener el ID del doctor
$idDoctor = isset($_REQUEST['idDoctor']) ? intval($_REQUEST['idDoctor']) : null;

if (!$idDoctor) {
    echo json_encode(['resultado' => 0, 'message' => 'Falta el ID del doctor']);
    exit;
}

// Conexión a la base de datos
$conn = new mysqli("localhost", "root", "", "farmacia");

if ($conn->connect_error) {
    echo json_encode(['resultado' => 0, 'message' => 'Error de conexión']);
    exit;
}

$conn->set_charset("utf8");

// Eliminar recetas asociadas
$stmtRecetas = $conn->prepare("DELETE FROM receta WHERE idDoctor = ?");
$stmtRecetas->bind_param("i", $idDoctor);
$stmtRecetas->execute();
$stmtRecetas->close();

// Eliminar doctor
$stmtDoctor = $conn->prepare("DELETE FROM doctor WHERE idDoctor = ?");
$stmtDoctor->bind_param("i", $idDoctor);
$stmtDoctor->execute();

// Confirmar eliminación
if ($stmtDoctor->affected_rows === 1) {
    $respuesta['resultado'] = 1;
    $respuesta['message'] = 'Doctor eliminado correctamente';
} else {
    $respuesta['message'] = 'No se encontró o no se pudo eliminar el doctor';
}

$stmtDoctor->close();
$conn->close();

echo json_encode($respuesta);
?>
