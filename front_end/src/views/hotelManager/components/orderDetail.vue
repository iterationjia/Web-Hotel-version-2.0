<template>
    <div>
        <a-modal
                :visible="orderDetailVisible"
                title="订单详情"
                width="900px"
                :footer="null"
                @cancel="cancel"

        >
            <div>
                <h1>dddddd</h1>
            </div>
        </a-modal>
    </div>
</template>

<script>
    import { mapGetters,mapMutations } from 'vuex'
    export default {
        name: "orderDetail",
        computed: {
            ...mapGetters([
                'orderDetailVisible',
            ])
        },
        methods: {
            ...mapMutations([
                'set_orderDetailVisible'
            ]),
            cancel(){
                this.set_orderDetailVisible(false)
            }
        }
    }
</script>

<style scoped>

</style>