package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Mesa")
public class Mesa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdMesa")
    private Integer idMesa;
    
    @Column(name = "NumComensales")
    private Integer numComensales;
    
    @Column(name = "Reserva")
    private Integer reserva;
    
    // Relación: Una mesa puede tener muchas facturas
    @OneToMany(mappedBy = "mesa", cascade = CascadeType.ALL)
    private List<Factura> facturas = new ArrayList<>();
    
    // Constructores
    public Mesa() {
    }
    
    public Mesa(Integer numComensales, Integer reserva) {
        this.numComensales = numComensales;
        this.reserva = reserva;
    }
    
    // Getters y Setters
    public Integer getIdMesa() {
        return idMesa;
    }
    
    public void setIdMesa(Integer idMesa) {
        this.idMesa = idMesa;
    }
    
    public Integer getNumComensales() {
        return numComensales;
    }
    
    public void setNumComensales(Integer numComensales) {
        this.numComensales = numComensales;
    }
    
    public Integer getReserva() {
        return reserva;
    }
    
    public void setReserva(Integer reserva) {
        this.reserva = reserva;
    }
    
    public List<Factura> getFacturas() {
        return facturas;
    }
    
    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
    }
    
    @Override
    public String toString() {
        return "Mesa{" +
                "idMesa=" + idMesa +
                ", numComensales=" + numComensales +
                ", reserva=" + reserva +
                '}';
    }
}
