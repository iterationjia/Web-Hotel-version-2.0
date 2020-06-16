<template>
    <a-list
            :data-source="hotelComments"
            :header="`${hotelComments.length} 条评价`"
            item-layout="horizontal"
    >
        <a-list-item slot="renderItem" slot-scope="item">
            <a-comment
                    :author="item.author"
                    :avatar="'data:image/jpeg;base64,'+item.avatar"
            >
                <span slot="actions">
                    <a-rate :value="item.star" disabled></a-rate>
                    {{item.star}}分
                </span>
                <p slot="content">
                    {{ item.comment }}
                </p>
                <a-tooltip slot="datetime">
                    <span>{{item.date}}</span>
                </a-tooltip>
            </a-comment>
        </a-list-item>
    </a-list>
</template>

<script>
    import {mapGetters} from 'vuex'
    export default {
        name: "hotelComments",
        computed:{
            ...mapGetters([
                'hotelComments'
            ])
        }
    }
</script>

<style scoped>

</style>