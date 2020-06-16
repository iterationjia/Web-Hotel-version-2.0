<template>
    <a-layout>
        <a-layout-content>
            <div class="hotelDetailCard">
                <h1>
                    {{ currentHotelInfo.title }}
                </h1>
                <div class="hotel-info">
                    <a-card style="width: 240px">
                        <img
                            alt="example"
                            v-if="currentHotelInfo.img==null"
                            src="@/assets/cover.jpeg"
                            slot="cover"
                            referrerPolicy="no-referrer"
                            />
                        <img
                                alt="example"
                                v-else
                                :src="'data:image/jpeg;base64,'+currentHotelInfo.img"
                                slot="cover"
                                referrerPolicy="no-referrer"
                        />
                    </a-card>
                    <div class="info">
                        <div class="items" v-if="currentHotelInfo.name">
                            <span class="label">酒店名称:</span>
                            <span class="value">{{ currentHotelInfo.name }}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.address">
                            <span class="label">地址:</span>
                            <span class="value">{{ currentHotelInfo.address }}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.rate">
                            <span class="label">评分:</span>
                            <a-rate style="font-size: 15px" :value="currentHotelInfo.rate" disabled allowHalf/>
                            <span class="value">{{ currentHotelInfo.rate }}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.hotelStar">
                            <span class="label">星级:</span>
                            <span class="value">{{ currentHotelInfo.hotelStar=='Three'?"三星级":currentHotelInfo.hotelStar=='Four'?"四星级":"五星级" }}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.description">
                            <span class="label">酒店简介:</span> 
                            <span class="value">{{ currentHotelInfo.description }}</span>
                        </div>
                    </div>
                </div>
                <a-divider></a-divider>
                <a-tabs>
                    <a-tab-pane tab="房间信息" key="1">
                        <RoomList :rooms="currentHotelInfo.rooms"></RoomList>
                    </a-tab-pane>
                    <a-tab-pane tab="在本店的订单" key="2">
                        <HotelOrderList :orders="userHotelOrderList"></HotelOrderList>
                    </a-tab-pane>
                    <a-tab-pane tab="评价" key="3">
                        <HotelComments></HotelComments>
                    </a-tab-pane>
                </a-tabs>
            </div>
        </a-layout-content>
    </a-layout>
</template>
<script>
import { mapGetters, mapActions, mapMutations } from 'vuex'
import RoomList from './components/roomList'
import HotelOrderList from './components/hotelOrderList'
import HotelComments from './components/hotelComments'
export default {
    name: 'hotelDetail',
    components: {
        RoomList,
        HotelOrderList,
        HotelComments
    },
    data() {
        return {
        }
    },
    computed: {
        ...mapGetters([
            'currentHotelInfo',
            'userHotelOrderList'
        ])
    },
    mounted() {
        this.set_currentHotelId(Number(this.$route.params.hotelId))
        this.getHotelById()
        this.getOrderListByUserAndHotel()
        this.getHotelComments()
    },
    beforeRouteUpdate(to, from, next) {
        this.set_currentHotelId(Number(to.params.hotelId))
        this.getHotelById()
        this.getOrderListByUserAndHotel()
        next()
    },
    methods: {
        ...mapMutations([
            'set_currentHotelId',
        ]),
        ...mapActions([
            'getHotelById',
            'getOrderListByUserAndHotel',
            'getHotelComments'
        ])
    }
}
</script>
<style scoped lang="less">
    .hotelDetailCard {
        padding: 50px 50px;
    }
    .hotel-info {
        display: flex;
        align-items: stretch;
        justify-content: flex-start;
        .info{
            padding: 10px 0;
            display: flex;
            flex-direction: column;
            margin-left: 20px;
            .items {
                display: flex;
                align-items: center;
                margin-bottom: 10px;
                .label{
                    margin-right: 10px;
                    font-size: 18px;
                }
                .value {
                    margin-right: 15px
                }
            }
        }
    }
</style>