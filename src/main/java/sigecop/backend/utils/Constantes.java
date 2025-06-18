/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sigecop.backend.utils;

/**
 *
 * @author Moises_F16.7.24
 */
public class Constantes {

    public static class EstadoCotizacion {

        public static final Integer GENERADO = 1;
        public static final Integer ARCHIVADO = 2;
        public static final Integer APROBADO = 3;
    }

    public static class EstadoSolicitud {

        public static final Integer EN_PROCESO = 1;
        public static final Integer FINALIZADO = 2;
    }

    public static class EstadoPedido {

        public static final Integer GENERADO = 1;
        public static final Integer ENVIADO = 2;
        public static final Integer CON_CONFORMIDAD = 3;
        public static final Integer DEVUELTO = 4;
        public static final Integer PAGADO = 5;
    }
    
    public static class TipoArchivo {

        public static final Integer GUIA = 1;
        public static final Integer FACTURA = 2;
    }
    public static class RutaUpload {
        public static final String DIR_GUIA = "uploadsGuia";
        public static final String DIR_FACTURA = "uploadsFactura";
    }
}
