package example.domain.store.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QStore is a Querydsl query type for Store
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStore extends EntityPathBase<Store> {

    private static final long serialVersionUID = -47212343L;

    public static final QStore store = new QStore("store");

    public final StringPath address = createString("address");

    public final StringPath category = createString("category");

    public final StringPath detaillocation = createString("detaillocation");

    public final StringPath instagram = createString("instagram");

    public final StringPath introduction = createString("introduction");

    public final NumberPath<Float> latitude = createNumber("latitude", Float.class);

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final StringPath location = createString("location");

    public final NumberPath<Float> longitude = createNumber("longitude", Float.class);

    public final StringPath name = createString("name");

    public final StringPath number = createString("number");

    public final StringPath profile = createString("profile");

    public final NumberPath<Integer> storeid = createNumber("storeid", Integer.class);

    public final StringPath type = createString("type");

    public final StringPath usertype = createString("usertype");

    public QStore(String variable) {
        super(Store.class, forVariable(variable));
    }

    public QStore(Path<? extends Store> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStore(PathMetadata metadata) {
        super(Store.class, metadata);
    }

}

