<template>
    <div>
        <a-modal
                :visible="orderDetailVisible"
                title="订单详情"
                :footer="null"
                @cancel="cancel"

        >
            <a-row>
                <a-col :span="8">
                    <a-statistic title="订单号" :value="info.id"/>
                </a-col>
                <a-col :span="8">
                    <a-statistic title="酒店名称" :value="info.hotelName"/>
                </a-col>
                <a-col :span="8">
                    <a-statistic title="酒店编号" :value="info.hotelId"/>
                </a-col>
            </a-row>
            <a-row>
                <a-col :span="8">
                    <a-statistic title="用户名称" :value="info.clientName"/>
                </a-col>
                <a-col :span="16">
                    <a-statistic title="用户手机号" :value="info.phoneNumber"/>
                </a-col>
            </a-row>
            <a-row>
                <a-col :span="8">
                    <a-statistic title="房间类型" :value="info.roomType"/>
                </a-col>
                <a-col :span="8">
                    <a-statistic title="房间数目" :value="info.roomNum"/>
                </a-col>
                <a-col :span="8">
                    <a-statistic title="入住人数" :value="info.peopleNum"/>
                </a-col>
            </a-row>
            <a-row>
                <a-col :span="12">
                    <a-statistic title="下单时间" :value="info.createDate"/>
                </a-col>
                <a-col :span="12">
                    <a-statistic title="订单状态" :value="info.orderState"/>
                </a-col>
            </a-row>
            <a-row>
                <a-col :span="12">
                    <a-statistic title="入住时间" :value="info.checkInDate"/>
                </a-col>
                <a-col :span="12">
                    <a-statistic title="离店时间" :value="info.checkOutDate"/>
                </a-col>
            </a-row>
            <a-row v-if="info.star!=null">
                <a-col :span="24">
                    <a-statistic :value="info.comment">
                        <span slot="title">
                            <div>评价</div>
                            <a-rate :default-value="info.star" disabled /> {{info.star}}分
                        </span>
                    </a-statistic>
                </a-col>
            </a-row>
        </a-modal>
    </div>
</template>

<script>
    import { mapGetters,mapMutations } from 'vuex'
    export default {
        name: "orderDetail",
        props: ['info'],
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