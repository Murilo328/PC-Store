package br.com.joaocarloslima.store.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;

class ComputadorTest {

    // Mesmos componentes cadastrados no StoreController
    private final Cpu i5 = new Cpu(1L, "Intel Core i5", 250, 1200.00, Socket.LGA1151);
    private final Cpu i7 = new Cpu(2L, "Intel Core i7", 300, 2000.00, Socket.LGA1200);
    private final Cpu ryzen = new Cpu(3L, "AMD Ryzen 5", 200, 1500.00, Socket.LGA1700);

    private final PlacaMae gigabyte = new PlacaMae(2L, "Gigabyte AORUS", 120, 900.00, Socket.LGA1200, TipoMemoria.DDR5);
    private final PlacaMae asus = new PlacaMae(1L, "ASUS ROG Strix", 100, 800.00, Socket.LGA1151, TipoMemoria.DDR4);
    private final PlacaMae msi = new PlacaMae(3L, "MSI B450", 90, 700.00, Socket.LGA1700, TipoMemoria.DDR4);

    private final Ram corsair = new Ram(1L, "Corsair Vengeance", 50, 400.00, TipoMemoria.DDR4, 16);
    private final Ram gskill = new Ram(2L, "G.Skill Ripjaws", 60, 350.00, TipoMemoria.DDR5, 32);

    private final Fonte fonte400 = new Fonte(1L, "Corsair 400W", 10, 300.00, 400);
    private final Fonte fonte650 = new Fonte(2L, "EVGA 650W", 12, 400.00, 650);

    private Computador montar(PlacaMae placa, Cpu cpu, Ram ram, Fonte fonte) {
        Computador c = new Computador();
        c.setPlacaMae(placa);
        c.setCpu(cpu);
        c.setRam(ram);
        c.setFonte(fonte);
        return c;
    }

    @Test
    void componenteDeveSerAbstratoEHerdadoPorTodos() {
        assertTrue(Modifier.isAbstract(Componente.class.getModifiers()));
        assertTrue(i5 instanceof Componente);
        assertTrue(asus instanceof Componente);
        assertTrue(corsair instanceof Componente);
        assertTrue(fonte400 instanceof Componente);
    }

    @Test
    void construtoresDevemInicializarAtributosDaBaseEDaClasseFilha() {
        assertEquals(Long.valueOf(1L), corsair.getId());
        assertEquals("Corsair Vengeance", corsair.getNome());
        assertEquals(50, corsair.getConsumo());
        assertEquals(400.00, corsair.getPreco(), 0.0001);
        assertEquals(TipoMemoria.DDR4, corsair.getTipo());
        assertEquals(16, corsair.getTamanhoGb());
        assertEquals(400, fonte400.getPotencia());
        assertEquals(Socket.LGA1151, i5.getSocket());
        assertEquals(Socket.LGA1200, gigabyte.getSocket());
        assertEquals(TipoMemoria.DDR5, gigabyte.getTipoMemoria());
    }

    @Test
    void placaMaeDeveValidarCompatibilidadeDeCpu() {
        assertTrue(asus.compativel(i5));
        assertFalse(asus.compativel(i7));
        assertTrue(gigabyte.compativel(i7));
        assertTrue(msi.compativel(ryzen));
        assertFalse(msi.compativel(i7));
        assertFalse(asus.compativel((Cpu) null));
    }

    @Test
    void placaMaeDeveValidarCompatibilidadeDeRam() {
        assertTrue(asus.compativel(corsair));
        assertFalse(asus.compativel(gskill));
        assertTrue(gigabyte.compativel(gskill));
        assertFalse(gigabyte.compativel(corsair));
        assertFalse(asus.compativel((Ram) null));
    }

    @Test
    void computadorVazioOuParcialDeveEstarIncompleto() {
        assertEquals("Computador incompleto", new Computador().status());
        assertEquals("Computador incompleto", montar(null, i5, corsair, fonte400).status());
        assertEquals("Computador incompleto", montar(asus, null, corsair, fonte400).status());
        assertEquals("Computador incompleto", montar(asus, i5, null, fonte400).status());
        assertEquals("Computador incompleto", montar(asus, i5, corsair, null).status());
    }

    @Test
    void deveDetectarIncompatibilidadeEntreCpuEPlacaMae() {
        // caso da imagem do enunciado: i7 + MSI + G.Skill + Corsair 400W
        assertEquals("Incompatibilidade entre CPU e Placa Mãe", montar(msi, i7, gskill, fonte400).status());
    }

    @Test
    void deveDetectarIncompatibilidadeEntreRamEPlacaMae() {
        assertEquals("Incompatibilidade entre RAM e Placa Mãe", montar(asus, i5, gskill, fonte650).status());
    }

    @Test
    void incompatibilidadeDeCpuTemPrioridadeSobreRamEFonte() {
        assertEquals("Incompatibilidade entre CPU e Placa Mãe", montar(asus, i7, gskill, fonte400).status());
    }

    @Test
    void deveDetectarFonteInsuficiente() {
        // i7 (300) + Gigabyte (120) + G.Skill (60) = 480 > 400
        assertEquals("Fonte insuficiente para o sistema", montar(gigabyte, i7, gskill, fonte400).status());
    }

    @Test
    void consumoIgualAPotenciaDaFonteEhAceito() {
        // i5 (250) + ASUS (100) + Corsair (50) = 400 -> não é "maior que" 400
        assertEquals("Computador completo e funcionando", montar(asus, i5, corsair, fonte400).status());
    }

    @Test
    void consumoDaPropriaFonteNaoEntraNaSoma() {
        // se a fonte (10W) fosse somada, daria 410 > 400 e o caso acima falharia
        Fonte fonteComConsumoAlto = new Fonte(9L, "Fonte teste", 500, 100.00, 400);
        assertEquals("Computador completo e funcionando", montar(asus, i5, corsair, fonteComConsumoAlto).status());
    }

    @Test
    void deveEstarCompletoEFuncionando() {
        assertEquals("Computador completo e funcionando", montar(gigabyte, i7, gskill, fonte650).status());
        assertEquals("Computador completo e funcionando", montar(msi, ryzen, corsair, fonte400).status());
    }

    @Test
    void precoTotalDeveSomarComponentesEscolhidos() {
        assertEquals(0.0, new Computador().precoTotal(), 0.0001);
        assertEquals(1200.00, montar(null, i5, null, null).precoTotal(), 0.0001);
        // valor mostrado na imagem do enunciado: 2000 + 700 + 350 + 300
        assertEquals(3350.00, montar(msi, i7, gskill, fonte400).precoTotal(), 0.0001);
    }
}
