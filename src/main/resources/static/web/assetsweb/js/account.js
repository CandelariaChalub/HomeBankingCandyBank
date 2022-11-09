const { createApp } = Vue
createApp({
    data() {
        return {
            client: [],
            account: [],
            transactions: [],
            startDate: "",
            endDate: "",
            formateadorFecha: new Intl.DateTimeFormat('es-MX', {day:'numeric', month:'numeric', year:'numeric'}),
            moneyFormat: new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }),
            }},
    created() {
        const queryString = location.search
        const params = new URLSearchParams(queryString)
        const idAccount = params.get("idAccount")
        this.loadData(idAccount)

    },
methods: {
    loadData(idAccount){
        axios.get('/api/clients/current').then(response =>{
            this.client = response.data
            this.account = this.client.accounts.find(account => account.id==idAccount)
            this.transactions = this.account.transactions
            
        })

    },
    logOut() {
        axios.post('/api/logout').then(response =>
            window.location.href = "/public/index.html"
        )
    },
    formatearHora(dateInput) {
                const date = new Date(dateInput)
                let minutes = date.getMinutes() > 9 ? date.getMinutes() : "0" + date.getMinutes()
                return date.getHours() + ":" + minutes
    },
    }
},
).mount('#app')