const app = Vue.
    createApp({
    data() {
        return {
            client: {},
            cards: [],
            debitCards: [],
            creditCards: [],
            formateadorFecha: new Intl.DateTimeFormat('es-MX', {month:'numeric', year:'2-digit'})

        }
    },
    created() {
        this.loadData()
    },
    methods: {
        loadData (){
                axios.get("/api/clients/current")
                    .then(data => {
                        this.client = data.data;
                        this.cards = this.client.cards;

                        this.debitCards = this.cards.filter(card => card.type == 'DEBIT')
                        this.creditCards = this.cards.filter(card => card.type == 'CREDIT')
                    })
        },

        logOut() {
            axios.post('/api/logout').then(response =>
                window.location.href = "/public/index.html"
            )
        },
    },
    computed: {

    },
}).mount('#app')