
{
    id: 2123    
    name: 'Bagel'
    variant: 'Plain'
    supportedSupplements: "FIBE", "FIBX"
}

{

if (BAGP canHave FILE)
    bagp.addSupplement(FILE)

productSupportsSupplement (sku) {
    if (sku.startsWith("BGL")) (
        supports sku.startWith("FIL")
)
}