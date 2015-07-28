<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="l" uri="http://lab.epam.com/spider/locale" %>
<%--Вікно статистики поста--%>
<div class="b-popup" id="popup_post_stats">
    <div class="b-popup-content" style="height: 340px;">
        <h4><l:resource key="poststats"/></h4>
        <br>
        <table id="stats_table" class="col-lg-12">
            <tr>
                <td>
                    <div align="center"><l:resource key="subscribers"/></div>
                    <div align="center">
                        <h1 id="reach_total">0</h1>

                        <h1>/</h1>

                        <h1 id="reach_subscribers">0</h1></div>
                    <div align="center">
                        <l:resource key="amountofusers"/>
                    </div>
                </td>
                <td>
                    <div align="center"><l:resource key="feedback"/></div>
                    <div>
                        <table>
                            <tr>
                                <td style="text-align: center">
                                    <h1><i style="color: #6c6c6c;" class="fa fa-heart"></i><span id="likes"></span></h1>
                                </td>
                                <td style="text-align: center">
                                    <h1><i style="color: #6c6c6c;" class="fa fa-bullhorn"></i><span id="reposts"></span>
                                    </h1>
                                </td>
                                <td style="text-align: center">
                                    <h1><i style="color: #6c6c6c;" class="fa fa-comment"></i><span id="comments"></span>
                                    </h1>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div align="center"><l:resource key="like.share.comment.amount"/></div>
                </td>
            </tr>
            <tr>
                <td><h4 id="to_group">0</h4> <l:resource key="community.visits"/></td>
                <td><h4 id="hide_post">0</h4> <l:resource key="hide.post"/></td>
            </tr>
            <tr>
                <td><h4 id="join_group">0</h4> <l:resource key="community.join"/></td>
                <td><h4 id="report">0</h4> <l:resource key="report"/></td>
            </tr>
            <tr>
                <td><h4 id="links">0</h4> <l:resource key="links.followed"/></td>
                <td><h4 id="unsubscribe">0</h4> <l:resource key="unsubscribe"/></td>
            </tr>
        </table>
        <br>

        <div align="right" style="margin-right: 20px">
            <button class="btn btn-primary" onclick="closePostStats()"><l:resource key="ok"/></button>
        </div>
    </div>
    <script>
        function openPostStats(postId) {
            $.post(
                    "${pageContext.request.contextPath}/post?action=postStats",
                    {
                        post_id: postId
                    },
                    onAjaxSuccess
            );
            function onAjaxSuccess(data) {

                $('#reach_total').html(' ' + data.reach_total);
                $('#reach_subscribers').html(' ' + data.reach_subscribers);

                $('#likes').html(' ' + data.likes);
                $('#reposts').html(' ' + data.reposts);
                $('#comments').html(' ' + data.comments);

                $('#to_group').html(' ' + data.to_group);
                $('#hide_post').html(' ' + data.hide);
                $('#join_group').html(' ' + data.join_group);
                $('#report').html(' ' + data.report);
                $('#links').html(' ' + data.links);
                $('#unsubscribe').html(' ' + data.unsubscribe);

                $("#popup_post_stats").show();
            }
        }
        function closePostStats() {
            $("#popup_post_stats").hide();
        }
    </script>
</div>
<style>
    #stats_table td {
        vertical-align: top;
        text-align: left
    }

    #stats_table h1 {
        display: inline;
        font-weight: 900;
        color: #00a0df;
    }

    #stats_table h4 {
        margin-left: 25px;
        display: inline;
        font-weight: 600;
        color: #00a0df;
    }
</style>