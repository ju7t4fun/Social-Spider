<script src="${pageContext.request.contextPath}/assets/chart-master/Chart.js"></script>
<script src="${pageContext.request.contextPath}/js/chartjs-custom.js"></script>

<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="modal_stat" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content" style="width: 1000px; margin-left: -200px;">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">x</button>
                <h4 class="modal-title">Charts</h4>
            </div>
            <div class="modal-body">
                <div class="row" style="margin-left: 110px;">
                    <!-- chart morris start -->
                    <div class="col-lg-12">
                        <section class="panel">
                            <header class="panel-heading">
                                <Char>General Chart</Char>
                            </header>
                            <input id="startDate" name="date" type="date" max="2015-06-30" value="2015-06-30"
                                   placeholder="Start Date" class="form-control">

                            <input id="endDate" name="date" type="date" max="2015-06-30" value="2015-06-30"
                                   placeholder="End Date" class="form-control">

                            <a onclick="redrawChart()" class="btn btn-default">Draw</a>

                            <div class="panel-body">
                                <div class="tab-pane" id="chartjs">
                                    <div class="row">
                                        <!-- Line -->
                                        <div class="col-lg-6" style="margin-left: -150px;">
                                            <section class="panel">
                                                <header class="panel-heading" style="margin-left: 50px;">
                                                    Line
                                                </header>
                                                <div class="panel-body text-center">
                                                    <canvas id="line" height="500" width="930"></canvas>
                                                </div>
                                            </section>
                                        </div>
                                    </div>
                                    <!-- Bar -->
                                    <div class="row">
                                        <div class="col-lg-6">
                                            <section class="panel">
                                                <header class="panel-heading">
                                                    Bar
                                                </header>
                                                <div class="panel-body text-center">
                                                    <canvas id="bar" height="300" width="500"></canvas>
                                                </div>
                                            </section>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <!-- Pie -->
                                        <div class="col-lg-6">
                                            <section class="panel">
                                                <header class="panel-heading">
                                                    Pie
                                                </header>
                                                <div class="panel-body text-center">
                                                    <canvas id="pie" height="300" width="400"></canvas>
                                                </div>
                                            </section>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </section>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>