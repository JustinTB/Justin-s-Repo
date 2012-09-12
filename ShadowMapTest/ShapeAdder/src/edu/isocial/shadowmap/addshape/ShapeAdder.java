/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.shadowmap.addshape;

import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.TriMesh;
import com.jme.scene.shape.*;
import edu.isocial.shadowmap.ShadowMapCore;
import edu.isocial.shadowmap.shadowmap_api.ShadowMapCoreAPI;
import java.util.HashMap;
import org.jdesktop.mtgame.*;
import org.jdesktop.mtgame.processor.RotationProcessor;
import org.openide.util.Lookup;

/**
 *
 * @author justin
 */
public class ShapeAdder {
    private Node shapeNode;
    private static HashMap<String, ShapeNode> shapeMap;
    private Entity newEntity; 
    private ShadowMapCore shadowMapCore;
    
    static {
        shapeMap = new HashMap<String, ShapeNode>();

        
        shapeMap.put("BOX", new AbstractScenegraph() {

            @Override
            public TriMesh createMesh() {
                return new Box("Box", Vector3f.ZERO, 3, 3, 3);
            }
        
        
        });
        
        shapeMap.put("CAPSULE", new AbstractScenegraph() {

            @Override
            public TriMesh createMesh() {
                return new Capsule("Capsule", 10, 10, 3, 3, 3);
            }
        
        
        });
        
        shapeMap.put("CONE", new AbstractScenegraph() {

            @Override
            public TriMesh createMesh() {
                return new Cone("Cone", 10, 10, 3, 3, true);
            }
        
        
        });
        
        shapeMap.put("CYLINDER", new AbstractScenegraph() {

            @Override
            public TriMesh createMesh() {
                return new Cylinder("Cylinder", 10, 10, 3, 3, 3, true, false);
            }
        
        
        });
        
        shapeMap.put("DISK", new AbstractScenegraph() {

            @Override
            public TriMesh createMesh() {
                return new Disk("Disk", 10, 10, 3);
            }
        
        
        });
        
        shapeMap.put("DODECAHEDRON", new AbstractScenegraph() {

            @Override
            public TriMesh createMesh() {
                return new Dodecahedron("Dodecahedron", 3);
            }
        
        
        });
        
        shapeMap.put("DOME", new AbstractScenegraph() {

            @Override
            public TriMesh createMesh() {
                return new Dome("Dome", Vector3f.ZERO, 10, 10, 3, true);
            }
        
        
        });
//        
//        shapeMap.put("GEO SHERE", new AbstractScenegraph() {
//
//            @Override
//            public TriMesh createMesh() {
//                return new GeoSphere("Geo", false, 10);
//            }
//        
//        
//        });
//        
        shapeMap.put("HEXAGON", new AbstractScenegraph() {

            @Override
            public TriMesh createMesh() {
                return new Hexagon("Hexagon", 3);
            }
        
        
        });
        
        shapeMap.put("ICOSAHEDRON", new AbstractScenegraph() {

            @Override
            public TriMesh createMesh() {
                return new Icosahedron("Icosahedron", 3);
            }
        
        
        });
        
        shapeMap.put("OCTAHEDRON", new AbstractScenegraph() {

            @Override
            public TriMesh createMesh() {
                return new Octahedron("Octahedron", 3);
            }
        
        
        });
        

        shapeMap.put("PQ TORUS", new AbstractScenegraph() {

            @Override
            public TriMesh createMesh() {
                return new PQTorus("PQ Torus", 3, 3, 3, 3, 5, 10);
            }
        
        
        });
        
        shapeMap.put("PYRAMID", new AbstractScenegraph() {

            @Override
            public TriMesh createMesh() {
                return new Pyramid("Pyramid", 4, 4);
            }
        
        
        });
        
        shapeMap.put("QUAD", new AbstractScenegraph() {

            @Override
            public TriMesh createMesh() {
                return new Quad("Quad", 4, 4);
            }
        
        
        });

        
        shapeMap.put("SPHERE", new AbstractScenegraph() {

            @Override
            public TriMesh createMesh() {
                return new Sphere("Sphere", Vector3f.ZERO, 10, 10, 3);
            }
        
        });
        
        
        shapeMap.put("TORUS", new AbstractScenegraph() {

            @Override
            public TriMesh createMesh() {
                return new Torus("Torus", 10, 10, 2, 3);
            }
        
        
        });
        
        shapeMap.put("TUBE", new AbstractScenegraph() {

            @Override
            public TriMesh createMesh() {
                return new Tube("Tube", 3, 2, 3);
            }
        
        
        });
        
        shapeMap.put("TEAPOT", new AbstractScenegraph() {

            @Override
            public TriMesh createMesh() {
                Teapot teapot = new Teapot("Teapot");
                teapot.updateGeometryData();
                return teapot;
            }
        
        
        });
    }
    
    public ShapeAdder() {
//        shadowMapCore = Lookup.getDefault().lookup(ShadowMapCoreAPI.class);
    }
    
    public void createShape(float x, float y, float z, boolean transparent, String shape){
        shadowMapCore = ShadowMapCore.getShadowMapTest();//Lookup.getDefault().lookup(ShadowMapCoreAPI.class);
        WorldManager worldManager = shadowMapCore.getWorldManager();
        RenderComponent renderComponent = null;
        JMECollisionComponent collisionComponent = null;
        Entity entity = null;
        JMECollisionSystem collisionSystem = (JMECollisionSystem) worldManager.getCollisionManager().loadCollisionSystem(JMECollisionSystem.class);
        
        
        shapeNode = shapeMap.get(shape).createNode(x, y, z, transparent, shape);
        
        entity = new Entity(shape);
        renderComponent = worldManager.getRenderManager().createRenderComponent(shapeNode);
        collisionComponent = collisionSystem.createCollisionComponent(shapeNode);
        entity.addComponent(RenderComponent.class, renderComponent);
        entity.addComponent(CollisionComponent.class, collisionComponent);


        ProcessorCollectionComponent processorCollectionCompoenent = new ProcessorCollectionComponent();
        RotationProcessor rotationProcessor = new RotationProcessor("Teapot Rotator", worldManager,
                shapeNode, (float) (6.0f * Math.PI / 180.0f));
        processorCollectionCompoenent.addProcessor(rotationProcessor);
        entity.addComponent(ProcessorCollectionComponent.class, processorCollectionCompoenent);
//        content.add(entity);
        newEntity = entity;
        worldManager.addEntity(entity);    
    }

    Entity getNewEntity() {
        return newEntity;
    }

}
