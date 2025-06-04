<?php
header('Content-Type: application/json');
ini_set('display_errors', 0);
error_reporting(0);

$respuesta = ['resultado' => 0, 'message' => 'Error desconocido'];

// Capturar parámetros y validarlos
//$idDoctor = isset($_REQUEST['idDoctor']) ? intval($_REQUEST['idDoctor']) : null;
//$nombreDoctor = $_REQUEST['nombreDoctor'] ?? null;
//$especialidad = $_REQUEST['especialidad'] ?? null;
//$jvpm = $_REQUEST['jvpm'] ?? null;
//$telefonoDoctor = $_REQUEST['telefonoDoctor'] ?? null;
//$correoDoctor = $_REQUEST['correoDoctor'] ?? null;
// Leer JSON desde el cuerpo de la petición
$input = json_decode(file_get_contents("php://input"), true);

$idDoctor = isset($input['idDoctor']) ? intval($input['idDoctor']) : null;
$nombreDoctor = $input['nombreDoctor'] ?? null;
$especialidad = $input['especialidad'] ?? null;
$jvpm = $input['jvpm'] ?? null;
$telefonoDoctor = $input['telefonoDoctor'] ?? null;
$correoDoctor = $input['correoDoctor'] ?? null;

if (!$idDoctor || !$nombreDoctor || !$especialidad || !$jvpm || !$telefonoDoctor || !$correoDoctor) {
    echo json_encode(['resultado' => 0, 'message' => 'Faltan datos']);
    exit;
}

// Conexión a la base de datos
$conn = new mysqli("localhost", "root", "", "farmacia");

if ($conn->connect_error) {
    echo json_encode(['resultado' => 0, 'message' => 'Error de conexión']);
    exit;
}

$conn->set_charset("utf8");

// Preparar y ejecutar consulta UPDATE
$stmt = $conn->prepare("
    UPDATE doctor 
    SET nombreDoctor = ?, especialidad = ?, jvpm = ?, telefonoDoctor = ?, correoDoctor = ?
    WHERE idDoctor = ?
");

if (!$stmt) {
    echo json_encode(['resultado' => 0, 'message' => 'Error al preparar la consulta']);
    exit;
}

$stmt->bind_param("sssssi", $nombreDoctor, $especialidad, $jvpm, $telefonoDoctor, $correoDoctor, $idDoctor);
$stmt->execute();

// Affected rows puede ser 0 si no hubo cambios, pero eso no es error
if ($stmt->affected_rows >= 0) {
    $respuesta['resultado'] = 1;
    $respuesta['message'] = 'Doctor actualizado correctamente';
} else {
    $respuesta['message'] = 'No se pudo actualizar el doctor';
}

$stmt->close();
$conn->close();

echo json_encode($respuesta);
?>
