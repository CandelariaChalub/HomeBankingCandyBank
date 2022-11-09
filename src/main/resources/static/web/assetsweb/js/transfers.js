const { createApp } = Vue
createApp({
    data() {
        return {
            client: [],
            description: "",
            amount: "",
            sourceNumber: "",
            destinationNumber: "",
            selectAccount: "",
            sourceAccount: [],
            destinationAccount: [],
            moneyFormat: new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD'}),
            accounts: [],
            destinationClient: {},
        }
    },
    created() {
        this.loadData()

    },
    methods: {
        loadData() {
            axios.get('/api/clients/current').then(response => {
                this.client = response.data
                this.accounts = this.client.accounts
            })
        },
        getDestinationClient() {
            axios.get('/api/transactions/' + this.destinationNumber).then(response => {
                this.destinationClient = response.data
            })
        },
        createTransaction() {
            axios.post('/api/transactions?' + "description=" + this.description + "&amount=" + this.amount + "&sourceNumber=" + this.sourceNumber + "&destinationNumber=" + this.destinationNumber)
            .then(response =>
                window.location.href = "/web/accounts.html"
                ).catch(response =>
                    alert ("ERROR"))
        },
        findSourceAccount() {
            this.sourceAccount = this.accounts.filter (account => account.number == this.sourceNumber)
        },
        logOut() {
            axios.post('/api/logout').then(response =>
                window.location.href = "/public/index.html"
            )
        },
    },
},
).mount('#app')

