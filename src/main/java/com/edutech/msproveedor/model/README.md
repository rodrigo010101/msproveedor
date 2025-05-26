# OneToMany = uno a muchos

OneToMany(mappedBy = "proveedor", cascade = CascadeType.All, fetch= FecthType.LAZY)

# ManyToOne = mucho a uno

@ManyToOne
@JoinColumn(name= "idProveedor",nullable = false)
fk = idProveedor

mappedBy = mapeo desde la clase proveedor a la clase servicioContratado
cascade = propagacion de operaciones
LAZY = los datos se cargan solo cuando se acceden explicitamente
