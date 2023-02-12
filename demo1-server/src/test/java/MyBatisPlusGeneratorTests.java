import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import org.apache.commons.lang3.SystemUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.type.JdbcType;
import org.assertj.core.util.Maps;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;

@Disabled
class MyBatisPlusGeneratorTests {
    static DataSourceConfig.Builder DB_CONFIG =
            new DataSourceConfig.Builder(
                    "jdbc:mysql://localhost/demo",
                    "demo",
                    "demo")
                    .typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                        JdbcType jdbcType = metaInfo.getJdbcType();
                        if (JdbcType.TINYINT.equals(jdbcType)) {
                            return DbColumnType.INTEGER;
                        }
                        return typeRegistry.getColumnType(metaInfo);
                    });

    static String[] TABLES = new String[]{
//            "admin_menu",
//            "admin_role",
//            "admin_role_menu",
//            "admin_user",
//            "admin_user_role",
//            "tz_channel",
//            "tz_distribution_management",
//            "tz_distribution_bill",
//            "tz_distribution_spu",
            "t_user",
    };

    @Test
    void execute() {
        File userDir = SystemUtils.getUserDir();
        FastAutoGenerator.create(DB_CONFIG)
                .globalConfig(builder -> builder
                        .author("MyBatis Plus Generator")
                        .outputDir(new File(userDir, "/src/main/java").getAbsolutePath())
                        .disableOpenDir())
                .templateConfig(builder -> builder
                        .disable(TemplateType.CONTROLLER))
                .packageConfig(builder -> builder
                        .parent("wang.cuimin.nacos.demo")
                        .mapper("mapper")
                        .pathInfo(Maps.newHashMap(OutputFile.xml,
                                new File(userDir, "/src/main/resources/mapper").getAbsolutePath()))
                        .entity("entity"))
                .strategyConfig(builder -> builder
                        .addInclude(TABLES)
                        .addTablePrefix("t_")
                        .serviceBuilder()
                        .formatServiceFileName("%sService")
                        .mapperBuilder()
                        .mapperAnnotation(Mapper.class)
                        .entityBuilder()
//                        .versionColumnName("update_time") // 乐观锁
                        .enableLombok()
                        .enableChainModel()
                        .enableFileOverride())
                .execute();
    }
}
