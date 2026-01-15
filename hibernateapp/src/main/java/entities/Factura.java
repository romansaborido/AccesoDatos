package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Factura")
public class Factura {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdFactura")
    private Integer idFactura;
    
    // Relación: Muchas facturas pertenecen a una mesa
    @ManyToOne
    @JoinColumn(name = "IdMesa", nullable = false)
    private Mesa mesa;
    
    @Column(name = "TipoPago")
    private String tipoPago;
    
    @Column(name = "Importe")
    private Double importe;
    
    // Relación: Una factura puede tener muchos pedidos
    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    private List<Pedido> pedidos = new ArrayList<>();
    
    // Constructores
    public Factura() {
    }
    
    public Factura(Mesa mesa, String tipoPago, Double importe) {
        this.mesa = mesa;
        this.tipoPago = tipoPago;
        this.importe = importe;
    }
    
    // Getters y Setters
    public Integer getIdFactura() {
        return idFactura;
    }
    
    public void setIdFactura(Integer idFactura) {
        this.idFactura = idFactura;
    }
    
    public Mesa getMesa() {
        return mesa;
    }
    
    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }
    
    public String getTipoPago() {
        return tipoPago;
    }
    
    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }
    
    public Double getImporte() {
        return importe;
    }
    
    public void setImporte(Double importe) {
        this.importe = importe;
    }
    
    public List<Pedido> getPedidos() {
        return pedidos;
    }
    
    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
    
    @Override
    public String toString() {
        return "Factura{" +
                "idFactura=" + idFactura +
                ", tipoPago='" + tipoPago + '\'' +
                ", importe=" + importe +
                '}';
    }
}