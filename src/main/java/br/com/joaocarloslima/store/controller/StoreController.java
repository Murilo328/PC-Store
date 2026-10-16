package br.com.joaocarloslima.store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import br.com.joaocarloslima.store.model.Computador;
import br.com.joaocarloslima.store.model.Cpu;
import br.com.joaocarloslima.store.model.Fonte;
import br.com.joaocarloslima.store.model.PlacaMae;
import br.com.joaocarloslima.store.model.Ram;
import br.com.joaocarloslima.store.model.Socket;
import br.com.joaocarloslima.store.model.TipoMemoria;

import java.util.*;

@Controller
public class StoreController {

    private Computador computador = new Computador();

    private List<Cpu> cpus = new ArrayList<>();
    private List<PlacaMae> placasMae = new ArrayList<>();
    private List<Ram> rams = new ArrayList<>();
    private List<Fonte> fontes = new ArrayList<>();

    public StoreController() {
        cpus.add(new Cpu(1L, "Intel Core i5", 250, 1200.00, Socket.LGA1151));
        cpus.add(new Cpu(2L, "Intel Core i7", 300, 2000.00, Socket.LGA1200));
        cpus.add(new Cpu(3L, "AMD Ryzen 5", 200, 1500.00, Socket.LGA1700));
        placasMae.add(new PlacaMae(2L, "Gigabyte AORUS", 120, 900.00, Socket.LGA1200, TipoMemoria.DDR5));
        placasMae.add(new PlacaMae(1L, "ASUS ROG Strix", 100, 800.00, Socket.LGA1151, TipoMemoria.DDR4));
        placasMae.add(new PlacaMae(3L, "MSI B450", 90, 700.00, Socket.LGA1700, TipoMemoria.DDR4));
        rams.add(new Ram(1L, "Corsair Vengeance", 50, 400.00, TipoMemoria.DDR4, 16));
        rams.add(new Ram(2L, "G.Skill Ripjaws", 60, 350.00, TipoMemoria.DDR5, 32));
        rams.add(new Ram(3L, "Kingston HyperX", 40, 300.00, TipoMemoria.DDR4, 8));
        fontes.add(new Fonte(1L, "Corsair 400W", 10, 300.00, 400));
        fontes.add(new Fonte(2L, "EVGA 650W", 12, 400.00, 650));
        fontes.add(new Fonte(3L, "Thermaltake 750W", 15, 500.00, 750));
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("cpus", cpus);
        model.addAttribute("placasMae", placasMae);
        model.addAttribute("rams", rams);
        model.addAttribute("fontes", fontes);
        model.addAttribute("selecionado", computador);
        model.addAttribute("compatibilidade", computador.status());

        return "index";
    }

    @GetMapping("/selecionar/cpu/{id}")
    public String selecionarCpu(@PathVariable Long id) {
        cpus.stream().filter(cpu -> cpu.getId().equals(id)).findFirst().ifPresent(cpu -> computador.setCpu(cpu));
        return "redirect:/";
    }

    @GetMapping("/selecionar/placa-mae/{id}")
    public String selecionarPlacaMae(@PathVariable Long id) {
        placasMae.stream().filter(placaMae -> placaMae.getId().equals(id)).findFirst().ifPresent(placaMae -> computador.setPlacaMae(placaMae));
        return "redirect:/";
    }

    @GetMapping("/selecionar/ram/{id}")
    public String selecionarRam(@PathVariable Long id) {
        rams.stream().filter(ram -> ram.getId().equals(id)).findFirst().ifPresent(ram -> computador.setRam(ram));
        return "redirect:/";
    }

    @GetMapping("/selecionar/fonte/{id}")
    public String selecionarFonte(@PathVariable Long id) {
        fontes.stream().filter(fonte -> fonte.getId().equals(id)).findFirst().ifPresent(fonte -> computador.setFonte(fonte));
        return "redirect:/";
    }

}
