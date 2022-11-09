const { createApp } = Vue
createApp({
    data() {
        return {
            client: [],
            account: [],
            transactions: [],
            totalBalance: 0,
            formateadorFecha: new Intl.DateTimeFormat('es-MX', {day:'numeric', month:'numeric', year:'numeric'}),
            moneyFormat: new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }),
            }},
    created() {
        this.loadData()

    },
methods: {
    loadData(){
        axios.get('/api/clients/current').then(response =>{
            this.client = response.data
            this.client.accounts.forEach(account => {
                this.totalBalance = this.totalBalance + account.balance
                account.transactions.forEach(transaction => this.transactions.push(transaction))
            })
            
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