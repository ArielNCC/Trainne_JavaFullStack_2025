#!/bin/bash

echo "==================================="
echo "  AGENDA CONTACTOS - VERIFICACIÓN  "
echo "==================================="

echo ""
echo "1. Verificando Docker Compose..."
if docker-compose ps | grep -q "agendacontactos_mysql"; then
    echo "✅ MySQL container está ejecutándose"
else
    echo "❌ MySQL container no está ejecutándose"
    echo "   Ejecuta: docker-compose up -d"
    exit 1
fi

echo ""
echo "2. Verificando conexión a MySQL..."
if docker exec agendacontactos_mysql mysql -u agendauser -pagenda123 -e "USE agendacontactos; SELECT COUNT(*) as total FROM contactos;" 2>/dev/null; then
    echo "✅ Conexión a MySQL exitosa"
    echo "✅ Base de datos y tabla creadas"
    TOTAL=$(docker exec agendacontactos_mysql mysql -u agendauser -pagenda123 -e "USE agendacontactos; SELECT COUNT(*) FROM contactos;" -s -N 2>/dev/null)
    echo "✅ Contactos de prueba insertados: $TOTAL"
else
    echo "❌ Error conectando a MySQL"
    exit 1
fi

echo ""
echo "3. URLs disponibles:"
echo "   📱 Aplicación: http://localhost:8080"
echo "   🗄️  phpMyAdmin: http://localhost:8081"
echo ""
echo "4. Credenciales MySQL:"
echo "   Usuario: agendauser"
echo "   Contraseña: agenda123"
echo "   Base de datos: agendacontactos"
echo ""
echo "🚀 Todo está listo para usar!"
echo ""