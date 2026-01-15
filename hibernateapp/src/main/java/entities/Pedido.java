package entities;

import javax.persistence.*;

@Entity
@Table(name = "Pedido")
public class Pedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdPedido")
    private Integer idPedido;
    
    // Relación: Muchos pedidos pertenecen a una factura
    @ManyToOne
    @JoinColumn(name = "IdFactura", nullable = false)
    private Factura factura;
    
    // Relación: Muchos pedidos pueden tener el mismo producto
    @ManyToOne
    @JoinColumn(name = "IdProducto", nullable = false)
    private Producto producto;
    
    @Column(name = "Cantidad")
    private Integer cantidad;
    
    // Constructores
    public Pedido() {
    }
    
    public Pedido(Factura factura, Producto producto, Integer cantidad) {
        this.factura = factura;
        this.producto = producto;
        this.cantidad = cantidad;
    }
    
    // Getters y Setters
    public Integer getIdPedido() {
        return idPedido;
    }
    
    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }
    
    public Factura getFactura() {
        return factura;
    }
    
    public void setFactura(Factura factura) {
        this.factura = factura;
    }
    
    public Producto getProducto() {
        return producto;
    }
    
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    
    public Integer getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    
    @Override
    public String toString() {
        return "Pedido{" +
                "idPedido=" + idPedido +
                ", cantidad=" + cantidad +
                '}';
    }
}
