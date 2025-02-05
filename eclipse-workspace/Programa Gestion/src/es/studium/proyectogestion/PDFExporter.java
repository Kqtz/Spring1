package es.studium.proyectogestion;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import java.io.FileOutputStream;
import java.util.List;

public class PDFExporter {

    public static void exportToPDF(String title, List<String[]> data, String[] headers) {
        if (data.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay datos para exportar.");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar PDF");
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath() + ".pdf";
            Document document = new Document();

            try {
                PdfWriter.getInstance(document, new FileOutputStream(filePath));
                document.open();

                // **Título**
                Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
                Paragraph titleParagraph = new Paragraph(title + "\n\n", titleFont);
                titleParagraph.setAlignment(Element.ALIGN_CENTER);
                document.add(titleParagraph);

                // **Tabla**
                PdfPTable table = new PdfPTable(headers.length);
                table.setWidthPercentage(100);
                table.setSpacingBefore(10);

                // **Encabezados**
                for (String header : headers) {
                    addTableHeader(table, header);
                }

                // **Datos**
                for (String[] row : data) {
                    for (String cell : row) {
                        table.addCell(cell);
                    }
                }

                document.add(table);
                document.close();
                JOptionPane.showMessageDialog(null, "PDF generado exitosamente en: " + filePath);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al generar el PDF: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void addTableHeader(PdfPTable table, String headerTitle) {
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        PdfPCell header = new PdfPCell(new Phrase(headerTitle, headerFont));
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        header.setPadding(5);
        table.addCell(header);
    }
}
