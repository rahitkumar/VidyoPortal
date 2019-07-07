var newNode = false,
    nodes = null,
    links = null,
    reloadSVG = null,
    highlight = null,
    unhighlight = null,
    changeRouterCount = null,
    highlightedPool = null,
    addNewPool = null,
    removePool = null,
    removeConnection = null;

function hasD3GUI() {
    var returnValue = false;
    if (document.getElementsByTagName('svg')[0]) {
        returnValue = true;
    }
    return returnValue;
}

function clearD3GUI() {
    var tagToRemove = document.getElementsByTagName('svg')[0];
    tagToRemove.parentNode.removeChild(tagToRemove);
}

function showD3RouterPool() {
    var width = '100%',
        height = '100%';

    var force = d3.layout.force().size([width, height]).linkDistance(150).charge(-60).on("tick", tick);

    var svg = d3.select("#svgContainer").append("svg").attr("width", width).attr("height", height).on("click", mousedown);

    var g = svg.append('g').attr("class", "svg-g");

    nodes = force.nodes();
    links = force.links();
    var node = svg.select(".svg-g").selectAll(".node");

    restart();

    svg.on("contextmenu", function(data, index) {
        d3.event.preventDefault();
    });

    g.append('svg:defs').append('svg:marker').attr('id', 'end-arrow').attr('viewBox', '0 -5 10 10').attr('refX', 20).attr("refY", -1.5).attr('markerWidth', 12).attr('markerHeight', 12).attr('orient', 'auto').append('svg:path').attr('d', 'M0,-5L10,0L0,5').attr('fill', '#000');

    g.append('svg:defs').append('svg:marker').attr('id', 'start-arrow').attr('viewBox', '0 -5 10 10').attr('refX', -10).attr("refY", -1.5).attr('markerWidth', 12).attr('markerHeight', 12).attr('orient', 'auto').append('svg:path').attr('d', 'M10,-5L0,0L10,5').attr('fill', '#000');

    function mousedown(e) {
        if ((Ext.browser.is.IE && event.srcElement.tagName === "svg") || (!Ext.browser.is.IE && event.target.tagName === "svg")) {
            unhighlight();
            goBackToPoolsInGrid();
        }
    }

    /**
     *	Function to create the path to set text to.
     */
    function arcPath(d) {
        var start = d.get('source'),
            end = d.get('target'),
            dx = end.get('x') - start.get('x'),
            dy = end.get('y') - start.get('y'),
            dr = Math.sqrt(dx * dx + dy * dy),
            sweep = 0;
        return "M" + start.get('x') + "," + start.get('y') + "A0" + "," + dr + " 0 0," + sweep + " " + end.get('x') + "," + end.get('y');
    }

    /**
     *	Special function to set attributes to nodes and links
     */
    function tick() {
    //due to bugs in IE 11 and 10, this D3 is not smooth. this will work fine in chrome and firefox.
    //arrows are missing in IE11. found out that it is a bug in IE. the work around is applied below. Once IE fixed the bug, we can remove this.
        link.each(function() {this.parentNode.insertBefore(this, this); });
    // work around for IE
    
    
        textPath.attr("d", function(d) {
            return arcPath(d);
        });

        textPath1.attr("d", function(d) {
            return arcPath(d);
        });

        node.attr("cx", function(d) {
            return d.x;
        }).attr("cy", function(d) {
            return d.y;
        });
    }

    reloadSVG = function() {
        restart();
    };

    function dragstarted() {
        d3.select(this).classed("dragging", true);
    }

    function dragended(d) {
        d3.select(this).classed("dragging", false);
        if (!!d.dirty && !!d.modified && !!d.previousValues && (d.modified.x != d.previousValues.x || d.modified.y != d.previousValues.y)) {
            savePoolCoordinates(d.get('x'), d.get('y'));
        }
    }

    function dragmove(d, i) {
        d.px += d3.event.dx;
        d.py += d3.event.dy;
        d.x += d3.event.dx;
        d.y += d3.event.dy;

        var svgDimensions = svg[0][0].getBoundingClientRect();

        if (d3.event.y > 15 && svgDimensions.bottom - svgDimensions.top - 15 > d3.event.y && d3.event.x > 15 && svgDimensions.right - svgDimensions.left - 15 > d3.event.x) {

            d3.select(this).attr("transform", "translate(" + d3.event.x + "," + d3.event.y + ")");

            highlightedPool.set('x', d3.event.x);
            highlightedPool.set('y', d3.event.y);

            path_label.attr("transform", function(d) {
                var x = (d.get('source').get('x') + d.get('target').get('x')) / 2;
                var y = (d.get('source').get('y') + d.get('target').get('y')) / 2;

                var yDiff = d.get('target').get('y') - d.get('source').get('y');
                var xDiff = d.get('target').get('x') - d.get('source').get('x');

                var slope = -yDiff / xDiff;
                //Taken -ve because y axis value increases vertically downwards which is in negation with ordinary x-y coordinate system.

                var theta = Math.atan(slope) * (180 / Math.PI);

                if (slope < 0) {
                    theta += 180;
                }

                if (yDiff > 0) {
                    theta += 180;
                }
                return "translate(" + x + "," + y + ")";
            });
            tick();
        }
    };

    /**
     *	Function to redraw the whole svg
     */
    function restart() {
        var node_drag = d3.behavior.drag().on("dragstart", dragstarted).on("drag", dragmove).on("dragend", dragended);

        Ext.each(nodes, function(rec) {
            if (!rec.get('free')) {
                var position = d3.select('#svgContainer')[0][0].getBoundingClientRect();
                if (position.right < rec.get('x')) {
                    rec.set('x', position.right - position.left - 20);
                }
                if (position.top < rec.get('y') && position.bottom < rec.get('y')) {
                    rec.set('y', position.bottom - position.top);
                }
                rec.commit();
            }
        });

        link = svg.select(".svg-g").selectAll("g.link").data(links);

        link.enter().append('g').attr('class', 'link');

        link.exit().remove();

        svg.selectAll('path.textpath').remove();
        svg.selectAll('path.hidden-textpath').remove();

        textPath = link.append("svg:path").attr("id", function(d) {
            return d.get('source').get('id') + "_" + d.get('source').get('id');
        }).attr("class", "textpath").style('marker-start', function(d) {
            return (d.get('direction') == 2) ? 'url(#start-arrow)' : '';
        }).style('marker-end', function(d) {
            return (d.get('direction') == 1) ? 'url(#end-arrow)' : '';
        });

        textPath1 = link.append("svg:path").attr("data-id1", function(d) {
            return d.get('source').get('id') + "_" + d.get('target').get('id');
        }).attr("class", "hidden-textpath");

        svg.selectAll('.path_label').remove();

        path_label = svg.select(".svg-g").selectAll(".path_label").data(links);
        path_label.enter().append("svg:text").attr("class", "path_label").attr("transform", function(d) {
            var x = (d.get('source').get('x') + d.get('target').get('x')) / 2;
            var y = (d.get('source').get('y') + d.get('target').get('y')) / 2;

            var yDiff = d.get('target').get('y') - d.get('source').get('y');
            var xDiff = d.get('target').get('x') - d.get('source').get('x');

            var slope = -yDiff / xDiff;
            //Taken -ve because y axis value increases vertically downwards which is in negation with ordinary x-y coordinate system.

            var theta = Math.atan(slope) * (180 / Math.PI);

            if (slope < 0) {
                theta += 180;
            }

            if (yDiff > 0) {
                theta += 180;
            }
            return "translate(" + x + "," + y + ")";
        }).text(function(d) {
            return d.get('weight');
        }).append("svg:textPath").attr("startOffset", "50%").attr("text-anchor", "middle").attr("xlink:href", function(d) {
            return "#" + d.get('source').get('id') + "_" + d.get('target').get('id');
        }).style("fill", "#000").style("font-family", "Arial");

        path_label.exit().remove();
        node = node.data(nodes);

        var newNode = node.enter().insert('g').attr('class', 'node').attr("transform", function(d) {
            return "translate(" + d.get('x') + "," + d.get('y') + ")";
        }).call(node_drag);

        newNode.append("text").attr("x", "0").attr("y", "25").attr("class", "node-text").text(function(d) {
            return Ext.htmlDecode(d.get('name'));
        });

        newNode.on('mousedown', function(d) {
            highlight(d);
        });

        newNode.append("circle", ".cursor").attr("class", function(d) {
            if (d.get('free')) {
                return "free-node";
            } else {
                return "node";
            }
        }).attr("r", 15);

        newNode.append("text").attr("x", "0").attr("y", "4").attr("class", "link-count").text(function(d) {
            return d.get('routerPoolMap').length;
        });

        node.exit().remove();

        force.start();
    }

    highlight = function(d, reloadUI) {
        highlightedPool = d;
        if (node) {
            node.style("opacity", function(o) {
                return isConnectedNodes(d, o) ? 1 : 0.2;
            });
        }
        if (path_label) {
            path_label.style("opacity", function(o) {
                return isConnectedLabel(d, o) ? 1 : 0.2;
            });
        }
        if (textPath) {
            textPath.style("opacity", function(o) {
                return isConnectedPath(d, o) ? 1 : 0.2;
            });
        }
        if (false !== reloadUI) {
            poolSelectionInGUI(d);
        }
    };

    getActiveNode = function() {
        var nodes = document.querySelectorAll('g.node'),
            translateString = "translate(" + highlightedPool.get('x') + (Ext.browser.is.IE ? " " : ",") + highlightedPool.get('y') + ")",
            activeNode;

        for (var i = 0; i < nodes.length; i++) {

            if (nodes[i].getAttribute('transform') == translateString) {
                activeNode = nodes[i];
            }
        }
        return activeNode;
    };

    changeRouterCount = function(newCount) {
        var activeNode = getActiveNode();
        //document.querySelectorAll('[cx="'+highlightedPool.x+'"]','[cy="'+highlightedPool.y+'"]')[0];
      //  activeNode.getElementsByClassName('link-count')[0].textContent = newCount; removed after we found out that it is not compatible with IE
        
       activeNode.querySelectorAll('.link-count')[0].textContent = newCount;
    };

    function isConnectedNodes(d, o) {
        var returnValue = false;
        for ( i = 0; i < links.length; ++i) {
            if ((d.get('id') === o.get('id')) || (links[i].get('source') === d.routerPoolMap && links[i].get('target') === o) || (links[i].get('source') === o && links[i].get('target') === d.routerPoolMap)) {
                returnValue = true;
                break;
            }
        }
        return d.get('id') == o.get('id');
    }

    function isConnectedLabel(d, o) {
        var returnValue = false;
        if (o.get('source') === d.routerPoolMap || o.get('target') === d.routerPoolMap) {
            returnValue = true;
        }
        return returnValue;
    }

    function isConnectedPath(d, o) {
        var returnValue = false;
        if (o.get('source') === d.routerPoolMap || o.get('target') === d.routerPoolMap) {
            returnValue = true;
        }
        return returnValue;
    }

    unhighlight = function() {
        highlightedPool = null;
        node.style('opacity', 1);
        restart();
    };
    addNewPool = function(newPool) {
        var svgNode = document.getElementsByTagName('svg')[0];
        var randomXY = getRandomCoordinates(svgNode);
        var randomX = randomXY[0],
            randomY = randomXY[1];

        newPool.set('x', randomX);
        newPool.set('y', randomY);
        nodes.push(newPool);
        restart();
    };

    getRandomCoordinates = function(svgNode) {
        var generateRandomNumber = function() {
            var randomX = Math.random() * (svgNode.clientWidth - 20) + 10;
            var randomY = Math.random() * (svgNode.clientHeight - 20) + 10;
            if(Ext.isFirefox) {
                var randomNumX = Math.floor(Math.random() * 10) + 1;
                var randomNumY = Math.floor(Math.random() * 3) + 1;  
                randomX = Math.floor(Math.random() * randomNumX * 100) + 50;
                randomY = Math.floor(Math.random() * randomNumY * 100) + 25;
            }
            return {
                x : randomX,
                y : randomY
            };
        },
            flag = false,
            array_x = [],
            array_y = [],
            randomXY = generateRandomNumber();
        Ext.each(nodes, function(node) {
            var x = node.get('x');
            var y = node.get('y');
            array_x.push(x);
            array_y.push(x);
            for (var i = 1; i < 35; i++) {
                array_x.push(x + i);
                array_y.push(y + i);
            }
            for (var i = 35; i > 0; i--) {
                array_x.push(x + i);
                array_y.push(y + i);
            }
        });

        if (Ext.Array.contains(randomXY.x, array_x) || Ext.Array.contains(randomXY.y, array_y)) {
            flag = true;
        }
        if (flag) {
            return getRandomCoordinates();
        }

        /*for (var nodeCounter = 0; nodeCounter < nodes.length; nodeCounter++) {
         if ((randomX > nodes[nodeCounter].get('x') - 15 && randomX < nodes[nodeCounter].get('x') + 15) || (randomY > nodes[nodeCounter].get('y') - 15 && randomY < nodes[nodeCounter].get('y') + 15)) {
         var randomXY = getRandomCoordinates(svgNode);
         randomX = randomXY[0];
         randomY = randomXY[1];
         break;

         }
         }*/

        var returnArray = new Array();
        returnArray.push(randomXY.x);
        returnArray.push(randomXY.y);
        return returnArray;
    };

    removePool = function(pool) {
        pool = pool.data;
        for ( i = 0; i < links.length; ++i) {
            if (links[i].source === pool || links[i].target === pool) {
                links.splice(i--, 1);
            }
        }
        nodes.splice(nodes.indexOf(pool), 1);
        var otherNodes = nodes;
        nodes = [];
        restart();
        nodes = otherNodes;
        restart();
    };

    removeConnection = function(connection) {
        links.splice(links.indexOf(connection), 1);
        restart();
    };
}
